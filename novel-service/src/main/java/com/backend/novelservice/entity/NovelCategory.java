package com.backend.novelservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

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
