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
public class NovelCommentRequest {
    String novelId;
    String novelName;
    String userId;
    String content;
    Integer replyCount;
    String ownerId;
}
