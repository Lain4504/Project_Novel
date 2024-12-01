package com.backend.searchservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelInfo {
    String novelId;
    Long categoryId;
    String categoryName;
    String picUrl;
    String bookName;
    Long authorId;
    Integer isVip;
    Integer bookStatus;
    Integer wordCountMin;
    Integer wordCountMax;
    Date updateTimeMin;
}
