package com.backend.notificationservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "notification_type")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationType {
    @MongoId
    String id;
    String contentType;
}
