package com.backend.commentservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Document(value = "comment_reply")
@Builder
public class NovelCommentReply {
    @MongoId
    String id;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
    String username;
    String userAvatar;
    String userOfReplyTo;
    String NovelName;

}
