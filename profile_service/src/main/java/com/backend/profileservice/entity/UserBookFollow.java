package com.backend.profileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Document(value = "user_book_follow")
public class UserBookFollow {
    @MongoId
    String id;
    String userId;
    String novelId;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    boolean isNotify;
}
