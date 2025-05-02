package com.upi.transaction_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    private String transactionId;
    private String senderUserId;
    private String recipientUserId;
    private BigDecimal amount;
    private String status; // PENDING, AUTHENTICATED, VALIDATED, DEBITED, CREDITED, COMPLETED, FAILED
    private LocalDateTime timestamp;
}
