package com.backend.commentservice.dto.request;

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
    String postId;
    String postName;
    String userId; // của người bình luận
    String content;
    Integer replyCount; // build khi get comment trong response
    String ownerId;
}
