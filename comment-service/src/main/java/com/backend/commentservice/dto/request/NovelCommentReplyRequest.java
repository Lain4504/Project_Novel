package com.backend.commentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @NotBlank(message = "Novel name is mandatory")
    String novelName;
    @NotNull(message = "Novel ID is mandatory")
    String novelId;
    @NotNull(message = "User ID of reply to is mandatory")
    String userIdOfReplyTo;
}
