package com.backend.novelservice.dto.request;

import java.time.LocalDateTime;

public class NovelChapterRequest {
    String volumeId;
    Integer chapterNumber;
    String chapterTitle;
    Integer wordCount;
    Boolean isVip;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
