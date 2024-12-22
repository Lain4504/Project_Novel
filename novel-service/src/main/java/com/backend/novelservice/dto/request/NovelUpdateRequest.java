package com.backend.novelservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelUpdateRequest {
    @NotBlank(message = "Title is mandatory")
    String title;
    @NotEmpty(message = "Categories cannot be empty")
    List<String> categories;
    @NotBlank(message = "Cover picture is mandatory")
    String imageUrl;
    @NotBlank(message = "Author ID is mandatory")
    String authorId;
    @NotBlank(message = "Author name is mandatory")
    String authorName;
    String description;
    @NotBlank(message = "Status is mandatory")
    String status;
}
