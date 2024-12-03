package com.backend.novelservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelVolumeRequest {
    String novelId;
    String volumeName;
    int volumeNumber;
    int chapterCount;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
}
