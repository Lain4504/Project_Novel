package com.backend.novelservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "prompt")
public class Prompt {
    @MongoId
    String id;
    String imageUrl;
    String redirectUrl;
    String title;
    String description;
    String type;
    String status;
    Instant createdAt;
    Instant updatedAt;
}
