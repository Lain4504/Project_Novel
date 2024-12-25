package com.backend.commentservice.entity;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Document(value = "post_comment")
public class PostComment {
    @MongoId
    String id;
    String postId;
    String postName; // lấy từ client
    String userId;
    String username; // lấy từ profile-service
    String content;
    @Column(nullable = false)
    Integer replyCount = 0;
    Instant createdDate;
    Instant updateDateTime;
    String userAvatar; // từ profle-service
    String ownerId;
}
