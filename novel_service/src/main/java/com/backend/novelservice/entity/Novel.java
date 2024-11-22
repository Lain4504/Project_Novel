package com.backend.novelservice.entity;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "novel")
public class Novel {
    @MongoId
    String id;
    String title;
    Long categoryId;
    String categoryName;
    String coverPicture;
    Long authorId;
    String authorName;
    String description;
    Integer score;
    Integer bookStatus;
    Long visitCount;
    Long wordCount;
    Long commentCount;
    Integer isVip;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
