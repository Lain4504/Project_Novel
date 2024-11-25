package com.backend.profileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Document(value = "user_comment_reply")
public class UserCommentReply {
    @MongoId
    String id;
    String userId;
    String commentId;
    String replyContent;
    Integer likeCount;
    Integer replyCount;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
