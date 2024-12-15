package com.backend.commentservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(value = "post_comment_reply")
public class PostCommentReply {
    @MongoId
    String id;
    String postName;
    String commentId;
    String parentReplyId;
    String userId;
    String replyContent;
    String replyTo;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
    String username;
    String userAvatar;
    String userIdOfReplyTo;
}
