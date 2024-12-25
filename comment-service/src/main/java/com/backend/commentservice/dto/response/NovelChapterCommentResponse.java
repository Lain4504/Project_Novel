package com.backend.commentservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class NovelChapterCommentResponse {
    String id;
    String chapterId;
    String userId;
    String content;
    Integer replyCount;
    String created;
    String username;
    String userAvatar;
    String ownerId;
    String chapterName;
}
