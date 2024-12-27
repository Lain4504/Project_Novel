package com.backend.novelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelElasticResponse {
    String id;
    Set<String> categoryId;
    Set<String> categoryName;
    String bookName;
    String authorId;
    String authorName;
    double score;
    String status;
    Long visitCount;
    Long wordCount;
    String lastChapterId;
    String lastChapterName;
    Long lastChapterUpdateTime = 0L;
    String image;
}
