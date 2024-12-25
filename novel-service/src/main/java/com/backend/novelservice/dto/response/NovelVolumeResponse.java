package com.backend.novelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NovelVolumeResponse {
    String id;
    String volumeName;
    String description;
    String status;
    int volumeNumber;
    int chapterCount;
    Instant createdDate;
    Instant modifiedDate;
    String novelId;
    List<String> chapterIds;
}