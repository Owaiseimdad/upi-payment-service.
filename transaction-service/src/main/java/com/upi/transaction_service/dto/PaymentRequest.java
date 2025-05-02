package com.upi.transaction_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private String senderUserId;
    private String recipientUserId;
    private BigDecimal amount;
    private String pin;
}
