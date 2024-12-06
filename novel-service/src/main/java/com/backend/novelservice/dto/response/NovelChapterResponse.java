package com.backend.novelservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

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
    String content;
    String status;
    Integer wordCount;
    Boolean isVip;
    String created;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
