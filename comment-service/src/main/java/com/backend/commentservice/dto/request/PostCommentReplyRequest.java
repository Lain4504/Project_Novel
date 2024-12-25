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
public class PostCommentReplyRequest {
    @NotBlank(message = "Post name is mandatory")
    String postName;
    @NotNull(message = "Post ID is mandatory")
    String postId;
    @NotNull(message = "Comment ID is mandatory")
    String commentId;
    @NotNull(message = "User ID is mandatory")
    String userId;
    @NotBlank(message = "Content reply cannot be blank")
    @Size(max = 500, message = "Content reply cannot exceed 500 characters")
    String replyContent;
    @NotNull(message = "Reply to cannot be null")
    String replyTo;
    @NotNull(message = "User ID Of Reply To cannot be blank")
    String userIdOfReplyTo;
}
