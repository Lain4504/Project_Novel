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
public class NovelCommentReplyRequest {
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    String chapterName;
    String chapterId;
    String userIdOfReplyTo;

}
