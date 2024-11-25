package com.backend.novelservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class NovelCategoryResponse {
    Long id;
    String name;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
}
