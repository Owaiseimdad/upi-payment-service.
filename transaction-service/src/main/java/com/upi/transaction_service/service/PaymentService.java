package com.upi.transaction_service.service;

import com.upi.transaction_service.dto.AccountRequest;
import com.upi.transaction_service.dto.AuthRequest;
import com.upi.transaction_service.dto.NotificationRequest;
import com.upi.transaction_service.dto.PaymentRequest;
import com.upi.transaction_service.entity.Transaction;
import com.upi.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    private static final String AUTH_SERVICE = "http://localhost:8081/api/auth/verify";
    private static final String ACCOUNT_SERVICE_VALIDATE = "http://localhost:8082/api/accounts/validate";
    private static final String ACCOUNT_SERVICE_DEBIT = "http://localhost:8082/api/accounts/debit";
    private static final String ACCOUNT_SERVICE_CREDIT = "http://localhost:8082/api/accounts/credit";
    private static final String NOTIFICATION_SERVICE = "http://localhost:8083/api/notifications/send";

    public String processPayment(PaymentRequest request) {
        // Step 1: Create Transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setSenderUserId(request.getSenderUserId());
        transaction.setRecipientUserId(request.getRecipientUserId());
        transaction.setAmount(request.getAmount());
        transaction.setStatus("PENDING");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        try {
            // Step 2: Authenticate
            Boolean authSuccess = restTemplate.postForObject(
                AUTH_SERVICE,
                new AuthRequest(request.getSenderUserId(), request.getPin()),
                Boolean.class
            );
            if (!Boolean.TRUE.equals(authSuccess)) {
                transaction.setStatus("FAILED");
                transactionRepository.save(transaction);
                return "Payment failed: Authentication failed";
            }
            transaction.setStatus("AUTHENTICATED");
            transactionRepository.save(transaction);

            // Step 3: Validate
            Boolean validateSuccess = restTemplate.postForObject(
                ACCOUNT_SERVICE_VALIDATE,
                request,
                Boolean.class
            );
            if (!Boolean.TRUE.equals(validateSuccess)) {
                transaction.setStatus("FAILED");
                transactionRepository.save(transaction);
                return "Payment failed: Validation failed (insufficient balance or invalid recipient)";
            }
            transaction.setStatus("VALIDATED");
            transactionRepository.save(transaction);

            // Step 4: Debit
            Boolean debitSuccess = restTemplate.postForObject(
                ACCOUNT_SERVICE_DEBIT,
                new AccountRequest(transaction.getTransactionId(), request.getSenderUserId(), request.getAmount()),
                Boolean.class
            );
            if (!Boolean.TRUE.equals(debitSuccess)) {
                transaction.setStatus("FAILED");
                transactionRepository.save(transaction);
                return "Payment failed: Debit failed";
            }
            transaction.setStatus("DEBITED");
            transactionRepository.save(transaction);

            // Step 5: Credit
            String creditTransactionId = UUID.randomUUID().toString();
            Boolean creditSuccess = restTemplate.postForObject(
                ACCOUNT_SERVICE_CREDIT,
                new AccountRequest(creditTransactionId, request.getRecipientUserId(), request.getAmount()),
                Boolean.class
            );
            if (!Boolean.TRUE.equals(creditSuccess)) {
                // Compensate: Reverse debit
                restTemplate.postForObject(
                    ACCOUNT_SERVICE_CREDIT,
                    new AccountRequest(UUID.randomUUID().toString(), request.getSenderUserId(), request.getAmount()),
                    Boolean.class
                );
                transaction.setStatus("FAILED");
                transactionRepository.save(transaction);
                return "Payment failed: Credit failed, debit reversed";
            }
            transaction.setStatus("CREDITED");
            transactionRepository.save(transaction);

            // Step 6: Notify
            try {
                restTemplate.postForObject(
                    NOTIFICATION_SERVICE,
                    new NotificationRequest(
                        request.getSenderUserId(),
                        request.getRecipientUserId(),
                        request.getAmount(),
                        transaction.getTransactionId(),
                        "COMPLETED"
                    ),
                    Boolean.class
                );
            } catch (Exception e) {
                // Log notification failure but don't fail the transaction
                System.out.println("Notification failed: " + e.getMessage());
            }
            transaction.setStatus("COMPLETED");
            transactionRepository.save(transaction);

            return "Payment completed: " + transaction.getTransactionId();
        } catch (Exception e) {
            transaction.setStatus("FAILED");
            transactionRepository.save(transaction);
            return "Payment failed: " + e.getMessage();
        }
    }
}
