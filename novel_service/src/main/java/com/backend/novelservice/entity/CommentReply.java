package com.backend.novelservice.entity;

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
public class CommentReply {
    @MongoId
    String id;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
