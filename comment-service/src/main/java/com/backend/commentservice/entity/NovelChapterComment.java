package com.backend.commentservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "chapter-comment")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelChapterComment {
    @MongoId
    String id;
    String chapterId;
    String userId;
    String content;
    Integer replyCount;
    Instant createdDate;
    Instant updateDateTime;
    String username;
    String userAvatar;
    String ownerId;
    String chapterName;
}
