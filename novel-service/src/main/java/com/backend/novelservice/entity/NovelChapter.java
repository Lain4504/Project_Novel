package com.backend.novelservice.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.*;

import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "chapter")
public class NovelChapter {
    @MongoId
    String id;
    String volumeId;
    Integer chapterNumber;
    String chapterTitle;
    String content;
    String status;
    Integer wordCount;
    Boolean isVip;
    Instant createdDate;
    Instant updateDateTime;
    @DBRef
    private NovelVolume volume;
}
