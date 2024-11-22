package com.backend.novelservice.entity;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.data.annotation.Id;

import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "chapter")
public class Chapter {
    @MongoId
    String id;
    String novelId;
    Integer chapterNumber;
    String chapterTitle;
    Integer wordCount;
    Boolean isVip;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
