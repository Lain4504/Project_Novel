package com.backend.novelservice.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelChapterRequest {
    @NotBlank(message = "Volume ID is mandatory")
    String volumeId;
    @Positive(message = "Chapter number must be positive")
    int chapterNumber;
    @NotBlank(message = "Chapter title is mandatory")
    String chapterTitle;
    @NotBlank(message = "Content is mandatory")
    String content;
    String status;
    @Positive(message = "Word count must be positive")
    int wordCount;
    @NotNull(message = "Novel ID is mandatory")
    Boolean isVip;
}
