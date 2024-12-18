package com.backend.postservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCategoryRequest {
    @NotBlank(message = "TITLE_REQUIRED")
    @Size(max = 100, message = "TITLE_TOO_LONG")
    String name;
    @NotBlank(message = "CONTENT_REQUIRED")
    String description;
}
