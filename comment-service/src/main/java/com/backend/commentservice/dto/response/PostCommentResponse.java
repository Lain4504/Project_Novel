package com.backend.commentservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class PostCommentResponse {
    String id;
    String postId;
    String postName;
    String userId;
    String content;
    Integer replyCount;
    String created;
    String username;
    String userAvatar;
    String ownerId;
}
