package com.backend.novelservice.entity;

import com.backend.enums.NovelStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "novel")
public class Novel {
    @MongoId
    String id;
    String title;
    @ManyToMany
    Set<NovelCategory> categories;
    String authorId;
    String authorName;
    String description;
    double score;
    Long visitCount;
    Long followCount;
    Long wordCount;
    Long commentCount;
    Integer isVip;
    Instant createdDate;
    Instant updateDateTime;
    List<String> volumeIds;
    @DBRef
    Image image;
    Integer chapterCount;
    String latestChapterTitle;
    String latestChapterId;
    Instant latestChapterTime;
    @Enumerated(EnumType.STRING)
    NovelStatusEnum status;
    Long ratingCount;
}
