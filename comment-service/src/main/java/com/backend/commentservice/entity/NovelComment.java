package com.backend.commentservice.entity;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Document(value = "novel_comment")
@Getter
@Setter
public class NovelComment {
    @MongoId
    String id;
    String novelId;
    String userId;
    String content;
    @Column(nullable = false)
    Integer replyCount = 0;
    Instant createdDate;
    Instant updateDateTime;
    String username;
    String userAvatar;
    String novelName;
    String ownerId;
}
