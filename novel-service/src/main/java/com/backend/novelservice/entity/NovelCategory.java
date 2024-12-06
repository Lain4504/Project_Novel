package com.backend.novelservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "category")
public class NovelCategory {
    @MongoId
    String id;
    String name;
    String description;
    Instant createdDate;
    Instant modifiedDate;
}
