package com.backend.novelservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelVolumeRequest {
    @NotBlank(message = "Volume name is mandatory")
    String volumeName;
    String description;
    @NotBlank(message = "Status is mandatory")
    String status;
    @Positive(message = "Volume number must be positive")
    int volumeNumber;
    @NotNull(message = "Novel ID is mandatory")
    String novelId;
    Set<String> chapters;
}
