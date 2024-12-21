package com.backend.searchservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelSearchRequest {
     String keyword;
     Integer categoryId;
     Integer bookStatus;
     Integer wordCountMin;
     Integer wordCountMax;
     Date updateTimeMin;
     String sort;
}
