package com.backend.notificationservice.service;

import com.backend.event.NotificationEvent;
import com.backend.notificationservice.entity.Notification;
import com.backend.notificationservice.entity.UserProfile;
import com.backend.notificationservice.enums.NotificationTemplate;
import com.backend.notificationservice.repository.NotificationRepository;
import com.backend.notificationservice.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {
    UserProfileRepository userProfileRepository;
    NotificationRepository notificationRepository;
    public void sendNotification(NotificationEvent message) {
        UserProfile userProfile = userProfileRepository.findByUserId(message.getRecipient());
        if (userProfile == null) {
            userProfile = UserProfile.builder()
                    .userId(message.getRecipient())
                    .build();
            userProfileRepository.save(userProfile);
        }
        String template = "";
        if (message.getTemplateCode().equals("COMMENT_OWNER_NOTIFICATION")) {
            template = NotificationTemplate.COMMENT_OWNER_NOTIFICATION.getTemplate()
                    .formatted(message.getParam().get("fromUser"), message.getParam().get("inLocation"), message.getParam().get("content"));
        }
        else if (message.getTemplateCode().equals("COMMENT_REPLY_NOTIFICATION")) {
            template = NotificationTemplate.COMMENT_REPLY_NOTIFICATION.getTemplate()
                    .formatted(message.getParam().get("fromUser"), message.getParam().get("inLocation"), message.getParam().get("content"));
        }

        Notification notification = Notification.builder()
                .title("New Comment")
                .content(template)
                .isRead(false)
                .NotificationType("COMMENT")
                .userId(message.getRecipient())
                .createdDate(Instant.now())
                .build();
        notificationRepository.save(notification);
        userProfileRepository.save(userProfile);
    }
    public List<Notification> getNotifications(String userId) {
        return notificationRepository.findAllByUserId(userId);
    }
}