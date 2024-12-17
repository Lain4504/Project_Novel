package com.backend.commentservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class NovelChapterCommentRequest {
    String chapterId;
    String userId;
    String content;
    Integer replyCount;
    String ownerId;
    String chapterName;
    String userIdOfReplyTo;
}
