package com.upi.notification_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class NotificationLog {
    @Id
    private String notificationId;
    private String senderUserId;
    private String recipientUserId;
    private BigDecimal amount;
    private String transactionId;
    private String status;
    private LocalDateTime timestamp;
}
