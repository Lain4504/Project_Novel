package com.backend.searchservice.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchResponse {
    String id;
    String categoryId;
    String categoryName;
    String novelName;
    String authorId;
    String authorName;
    String novelDesc;
    String novelStatus;
    int visitCount;
    int wordCount;
    int commentCount;
    String lastChapterId;
    String lastChapterName;
    String lastChapterUpdateTime;
    boolean isVip;
}
