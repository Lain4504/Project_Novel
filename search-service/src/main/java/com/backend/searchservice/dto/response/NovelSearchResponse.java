package com.backend.searchservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelSearchResponse {
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
