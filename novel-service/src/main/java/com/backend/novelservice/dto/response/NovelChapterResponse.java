package com.backend.novelservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

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
    Integer wordCount;
    Boolean isVip;
    String created;
    Instant createdDate;
    Instant updateDate;
    String authorId;
    String status;
}
