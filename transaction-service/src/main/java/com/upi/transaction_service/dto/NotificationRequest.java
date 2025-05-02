package com.upi.transaction_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotificationRequest {
    private String senderUserId;
    private String recipientUserId;
    private BigDecimal amount;
    private String transactionId;
    private String status;

    public NotificationRequest(String senderUserId, String recipientUserId, BigDecimal amount, String transactionId, String status) {
        this.senderUserId = senderUserId;
        this.recipientUserId = recipientUserId;
        this.amount = amount;
        this.transactionId = transactionId;
        this.status = status;
    }
}