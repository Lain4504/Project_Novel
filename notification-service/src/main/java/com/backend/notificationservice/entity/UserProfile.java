package com.backend.notificationservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(value = "user_profile")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserProfile {
    @MongoId
    String id;
    String userId;
    @DBRef
    @JsonBackReference
    List<Notification> notifications;
}
