package com.backend.commentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class PostCommentRequest {
    @NotNull(message = "PostId is mandatory")
    String postId;
    @NotBlank(message = "PostName is mandatory")
    String postName;
    @NotNull(message = "UserId is mandatory")
    String userId; // của người bình luận
    @NotBlank(message = "Content is mandatory")
    @Size(max = 500, message = "Content has to be less than 500 characters")
    String content;
    Integer replyCount; // build khi get comment trong response
    @NotNull(message = "OwnerId is mandatory")
    String ownerId;
}
