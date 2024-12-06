package com.backend.novelservice.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelCreationRequest {
    String title;
    List<String> categories;
    String coverPicture;
    Long authorId;
    String authorName;
    String description;
    LocalDateTime createdDate;
}