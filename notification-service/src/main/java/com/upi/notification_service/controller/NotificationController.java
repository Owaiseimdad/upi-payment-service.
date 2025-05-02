package com.upi.notification_service.controller;

import com.upi.notification_service.dto.NotificationRequest;
import com.upi.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/send")
    public boolean send(@RequestBody NotificationRequest request) {
        return notificationService.sendNotification(request);
    }
}