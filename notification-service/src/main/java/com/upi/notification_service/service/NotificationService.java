package com.upi.notification_service.service;

import com.upi.notification_service.dto.NotificationRequest;
import com.upi.notification_service.entity.NotificationLog;
import com.upi.notification_service.repository.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationLogRepository notificationLogRepository;

    public boolean sendNotification(NotificationRequest request) {
        // Simulate sending notifications (log to console)
        System.out.println("Notification to " + request.getSenderUserId() + ": Paid " + request.getAmount() + " to " + request.getRecipientUserId());
        System.out.println("Notification to " + request.getRecipientUserId() + ": Received " + request.getAmount() + " from " + request.getSenderUserId());

        // Log notification to database
        NotificationLog log = new NotificationLog();
        log.setNotificationId(UUID.randomUUID().toString());
        log.setSenderUserId(request.getSenderUserId());
        log.setRecipientUserId(request.getRecipientUserId());
        log.setAmount(request.getAmount());
        log.setTransactionId(request.getTransactionId());
        log.setStatus(request.getStatus());
        log.setTimestamp(LocalDateTime.now());
        notificationLogRepository.save(log);

        return true;
    }
}