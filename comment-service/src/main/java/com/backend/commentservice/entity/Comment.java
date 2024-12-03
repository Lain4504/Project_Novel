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
@Builder
@Document(value = "comment")
public class Comment {
    @MongoId
    String id;
    String postId;
    String userId;
    String content;
    Integer replyCount;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
