package com.backend.commentservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class NovelCommentReplyResponse {
    String id;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    String created;
    String username;
    String userAvatar;
    String userIdOfReplyTo;
    String novelName;
    String novelId;
}
