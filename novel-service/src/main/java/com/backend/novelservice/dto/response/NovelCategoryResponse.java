package com.backend.novelservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class NovelCategoryResponse {
    String id;
    String name;
    String description;
    String created;
    Instant createdDate;
    Instant modifiedDate;
}
