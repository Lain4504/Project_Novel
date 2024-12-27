package com.backend.searchservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelSearchResponse {
    String id;
    Set<String> categoryId;
    Set<String> categoryName;
    String bookName;
    String authorId;
    String description;
    String authorName;
    double score;
    String status;
    Long visitCount;
    Long wordCount;
    String lastChapterId;
    String lastChapterName;
    Long lastChapterUpdateTime;
    String image;
}
