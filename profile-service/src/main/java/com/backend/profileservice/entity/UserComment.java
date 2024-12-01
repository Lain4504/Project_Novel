package com.backend.profileservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "user_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class UserComment {
    @MongoId
    String id;
    String userId;
    String novelId;
    String comment;
    Integer likeCount;
    Integer replyCount;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;

}
