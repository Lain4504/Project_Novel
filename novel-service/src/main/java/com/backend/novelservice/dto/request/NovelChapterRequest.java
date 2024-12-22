package com.backend.novelservice.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelChapterRequest {
    @NotNull(message = "Volume ID is mandatory")
    String volumeId;
    @Positive(message = "Chapter number must be positive")
    int chapterNumber;
    @NotBlank(message = "Chapter title is mandatory")
    String chapterTitle;
    @NotBlank(message = "Content is mandatory")
    @Size(min = 1, message = "Content must not be empty")
    String content;
    String status;
    @Positive(message = "Word count must be positive")
    int wordCount;
    @NotNull(message = "Chapter type is mandatory")
    Boolean isVip;
}
