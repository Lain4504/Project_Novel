package com.backend.commentservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
