package com.backend.commentservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Document(value = "chapter-comment-reply")
public class NovelChapterCommentReply {
    @MongoId
    String id;
    String chapterName;
    String chapterId;
    String commentId;
    String userId;
    String replyContent;
    String replyTo;
    Instant createdDate;
    Instant updateDateTime;
    String username;
    String userAvatar;
    String userIdOfReplyTo;
}
