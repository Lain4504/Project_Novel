package com.backend.searchservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRequest {
     String keyword;
     Integer categoryId;
     Integer isVip;
     Integer bookStatus;
     Integer wordCountMin;
     Integer wordCountMax;
     Date updateTimeMin;
     String sort;
}
