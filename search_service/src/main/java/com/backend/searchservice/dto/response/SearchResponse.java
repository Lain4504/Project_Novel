package com.backend.searchservice.dto.response;

import com.backend.searchservice.entity.NovelInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchResponse {
    List<NovelInfo> novelInfos;
}
