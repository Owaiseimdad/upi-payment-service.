package com.upi.transaction_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    private String transactionId;
    private String userId;
    private BigDecimal amount;

    public AccountRequest(String transactionId, String userId, BigDecimal amount) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
    }
}