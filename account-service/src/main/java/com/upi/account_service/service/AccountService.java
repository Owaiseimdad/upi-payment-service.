package com.upi.account_service.service;

import com.upi.account_service.dto.AccountRequest;
import com.upi.account_service.dto.ValidateRequest;
import com.upi.account_service.entity.Account;
import com.upi.account_service.entity.TransactionLog;
import com.upi.account_service.repository.AccountRepository;
import com.upi.account_service.repository.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionLogRepository transactionLogRepository;

    public boolean validatePayment(ValidateRequest request) {
        Account senderAccount = accountRepository.findByUserId(request.getSenderUserId());
        Account recipientAccount = accountRepository.findByUserId(request.getRecipientUserId());
        return senderAccount != null &&
            recipientAccount != null &&
            senderAccount.getBalance().compareTo(request.getAmount()) >= 0;
    }

    public boolean debit(AccountRequest request) {
        // Check for idempotency
        if (transactionLogRepository.existsById(request.getTransactionId())) {
            return true; // Already processed
        }

        Account account = accountRepository.findByUserId(request.getUserId());
        if (account == null || account.getBalance().compareTo(request.getAmount()) < 0) {
            return false;
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        TransactionLog log = new TransactionLog();
        log.setTransactionId(request.getTransactionId());
        log.setAccountId(account.getAccountId());
        log.setAmount(request.getAmount());
        log.setType("DEBIT");
        transactionLogRepository.save(log);

        return true;
    }

    public boolean credit(AccountRequest request) {
        if (transactionLogRepository.existsById(request.getTransactionId())) {
            System.out.println("Credit skipped: Transaction " + request.getTransactionId() + " already processed");
            return true;
        }

        Account account = accountRepository.findByUserId(request.getUserId());
        if (account == null) {
            System.out.println("Credit failed: Account not found for user " + request.getUserId());
            return false;
        }

        System.out.println("Crediting " + request.getAmount() + " to " + request.getUserId() + ", current balance: " + account.getBalance());
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        TransactionLog log = new TransactionLog();
        log.setTransactionId(request.getTransactionId());
        log.setAccountId(account.getAccountId());
        log.setAmount(request.getAmount());
        log.setType("CREDIT");
        transactionLogRepository.save(log);

        System.out.println("Credit successful for " + request.getUserId() + ", new balance: " + account.getBalance());
        return true;
    }
}
