package com.backend.novelservice.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

import com.backend.enums.ChapterStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    Integer wordCount;
    Boolean isVip;
    Instant createdDate;
    Instant updateDate;
    @Enumerated(EnumType.STRING)
    ChapterStatusEnum status;
}
