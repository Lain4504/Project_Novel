package com.backend.novelservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelVolume {
    @MongoId
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
