package com.backend.novelservice.dto.request;

import com.backend.novelservice.dto.response.NovelChapterResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelChapterRequest {
    String volumeId;
    int chapterNumber;
    String chapterTitle;
    String content;
    String status;
    int wordCount;
    Boolean isVip;
    Instant createdDate;
    Instant updateDateTime;
}
