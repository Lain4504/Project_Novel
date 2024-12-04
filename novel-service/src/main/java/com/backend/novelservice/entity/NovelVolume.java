package com.backend.novelservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelVolume {
    String id;
    String novelId;
    String volumeName;
    int volumeNumber;
    int chapterCount;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
}
