package com.upi.account_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class TransactionLog {
    @Id
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private String type; // DEBIT or CREDIT
}
