package com.backend.novelservice.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelResponse {
    String id;
    String title;
    List<String> categories;
    String coverPicture;
    Long authorId;
    String authorName;
    String description;
    Integer score;
    Integer bookStatus;
    Long visitCount;
    Long wordCount;
    Long commentCount;
    Integer isVip;
    LocalDateTime createdDate;
    LocalDateTime updateDateTime;
}
