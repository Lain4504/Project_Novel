package com.backend.novelservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    @DBRef
    @JsonBackReference
    private Novel novel;
    @DBRef
    @JsonIgnore // Không trả danh sách chapters khi gọi API `getNovel`
    private List<NovelChapter> chapters;
}
