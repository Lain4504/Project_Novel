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
public class NovelChapterCommentReplyRequest {
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    String userIdOfReplyTo;
    String chapterName;
    String chapterId;
}
