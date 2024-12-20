package com.backend.searchservice.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "novel")
@Builder
@ToString
public class Novel {
     String id;
     String title;
     Set<String> categories;
     String authorId;
     String authorName;
     String description;
     Integer score;
     Integer bookStatus;
     Long visitCount;
     Long wordCount;
     Long commentCount;
     Integer isVip;
     Instant createdDate;
     Instant updateDateTime;
     List<String> volumeIds;
     String imageUrl;
     Integer chapterCount;
     String latestChapterTitle;
     String latestChapterId;
     Instant latestChapterTime;
     String status;
}