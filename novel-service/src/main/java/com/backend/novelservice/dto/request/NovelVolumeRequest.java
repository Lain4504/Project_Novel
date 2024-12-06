package com.backend.novelservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelVolumeRequest {
    String volumeName;
    String description;
    String status;
    int volumeNumber;
    int chapterCount;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    Set<String> chapters;
}
