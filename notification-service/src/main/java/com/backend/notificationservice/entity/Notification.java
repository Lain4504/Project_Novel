package com.backend.notificationservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "notification")
public class Notification {
    @MongoId
    String id;
    String title;
    String content;
    Boolean isRead;
    String NotificationType;
    String userId;
    Instant createdDate;
}
