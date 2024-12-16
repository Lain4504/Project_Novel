package com.backend.commentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelChapterCommentReply {
    @MongoId
    String id;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
    String username;
    String userAvatar;
    String userOfReplyTo;
    String chapterName;
}
