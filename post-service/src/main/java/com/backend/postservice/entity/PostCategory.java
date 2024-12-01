package com.backend.postservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "post_category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCategory {
    @MongoId
    String id;
    String name;
}
