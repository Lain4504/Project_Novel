package com.backend.novelservice.dto.response;

import com.backend.novelservice.entity.NovelCategory;
import com.backend.novelservice.entity.NovelVolume;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelResponse {
    String id;
    String title;
    Set<NovelCategory> categories;
    String coverPicture;
    String authorId;
    String authorName;
    String description;
    Integer score;
    Integer bookStatus;
    Long visitCount;
    Long wordCount;
    Long commentCount;
    Integer isVip;
    String created;
    Instant createdDate;
    Instant updateDateTime;
    List<NovelVolume> volumes;
}
