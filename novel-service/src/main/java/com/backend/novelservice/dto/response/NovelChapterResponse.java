package com.backend.novelservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelChapterResponse {
    String id;
    String volumeId;
    Integer chapterNumber;
    String chapterTitle;
    Integer wordCount;
    Boolean isVip;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
