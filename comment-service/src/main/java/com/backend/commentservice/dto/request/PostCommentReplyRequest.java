package com.backend.commentservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class PostCommentReplyRequest {
    String postName;
    String postId;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    String userIdOfReplyTo;
}
