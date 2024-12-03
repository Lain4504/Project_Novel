package com.backend.commentservice.entity;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Document(value = "comment")
@Getter
@Setter
public class NovelComment {
    @MongoId
    String id;
    String novelId;
    String userId;
    String content;
    Integer replyCount;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
