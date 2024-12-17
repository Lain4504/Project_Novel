package com.backend.commentservice.entity;

import com.backend.commentservice.repository.httpclient.UserProfileResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;

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
    Integer replyCount;
    Instant createdDate;
    Instant updateDateTime;
    String userAvatar; // từ profle-service
    String ownerId;
}
