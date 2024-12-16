package com.backend.notificationservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.event.NotificationEvent;
import com.backend.notificationservice.entity.Notification;
import com.backend.notificationservice.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @KafkaListener(topics = "post-comment-notification")
    public void listen(NotificationEvent message) {
        log.info("Received message: {}", message);
        try {
            notificationService.sendNotification(message);
            log.info("Notification sent to: {}", message.getRecipient());
        } catch (MessagingException e) {
            log.error("Error sending notification to {}: {}", message.getRecipient(), e.getMessage());
        }
    }
    @GetMapping("/{userId}")
    ApiResponse<List<Notification>> getNotificationByUserId(@PathVariable("userId") String userId) {
        return ApiResponse.<List<Notification>>builder().result(notificationService.getNotifications(userId)).build();
    }
}
