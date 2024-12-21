package com.backend.searchservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "novel")
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
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
     List<String> volumeIds;
     String imageUrl;
     Integer chapterCount;
     String latestChapterTitle;
     String latestChapterId;
     String status;
}