package com.backend.commentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Comment ID is mandatory")
    String commentId;
    @NotNull(message = "User ID is mandatory")
    String userId;
    @NotBlank(message = "Reply content is mandatory")
    @Size(max = 500, message = "Reply content must be between 1 and 500 characters")
    String replyContent;
    @NotBlank(message = "Reply to is mandatory")
    String replyTo;
    @NotBlank(message = "Chapter name is mandatory")
    String chapterName;
    @NotNull(message = "Chapter ID is mandatory")
    String chapterId;
    @NotNull(message = "User ID of reply to is mandatory")
    String userIdOfReplyTo;

}
