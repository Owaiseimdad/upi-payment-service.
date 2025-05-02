package com.upi.account_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ValidateRequest {
    private String senderUserId;
    private String recipientUserId;
    private BigDecimal amount;
}
