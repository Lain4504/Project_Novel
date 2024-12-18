package com.backend.postservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {
    @NotBlank(message = "TITLE_REQUIRED")
    @Size(max = 100, message = "TITLE_TOO_LONG")
    String title;
    @NotBlank(message = "CONTENT_REQUIRED")
    String content;
    @NotNull(message = "USER_ID_REQUIRED")
    String userId;
    @NotBlank(message = "CATEGORY_ID_REQUIRED")
    String categoryId;
}
