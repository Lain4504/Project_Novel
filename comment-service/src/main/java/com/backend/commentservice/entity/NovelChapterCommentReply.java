package com.backend.commentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelChapterCommentReply {
    String id;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
    String username;
    String userAvatar;
}
