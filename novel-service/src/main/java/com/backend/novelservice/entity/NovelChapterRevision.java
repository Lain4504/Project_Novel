package com.backend.novelservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(value = "chapter_revision")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NovelChapterRevision {
    @MongoId
    String id;
    String chapterId;
    String content;
    LocalDateTime revisionDate;
    String updatedBy;
}
