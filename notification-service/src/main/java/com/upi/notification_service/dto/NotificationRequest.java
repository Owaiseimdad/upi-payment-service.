package com.upi.notification_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotificationRequest {
    private String senderUserId;
    private String recipientUserId;
    private BigDecimal amount;
    private String transactionId;
    private String status;
}