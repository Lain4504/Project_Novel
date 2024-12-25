package com.backend.commentservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

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
    String postId;
    String postName;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    Instant createdDate;
    Instant updateDateTime;
    String username;
    String userAvatar;
    String userIdOfReplyTo;
}
