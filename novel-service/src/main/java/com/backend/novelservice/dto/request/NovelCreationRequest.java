package com.backend.novelservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelCreationRequest {
    @NotBlank(message = "Title is mandatory")
    String title;
    @NotEmpty(message = "Categories is mandatory")
    List<String> categories;
    @NotBlank(message = "Cover picture is mandatory")
    String coverPicture;
    @NotBlank(message = "Author ID is mandatory")
    String authorId;
    @NotBlank(message = "Author name is mandatory")
    String authorName;
    String description;
}
