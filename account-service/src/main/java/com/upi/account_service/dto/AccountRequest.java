package com.upi.account_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    private String transactionId;
    private String userId;
    private BigDecimal amount;
}
