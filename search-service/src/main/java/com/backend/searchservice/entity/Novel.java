package com.backend.searchservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "novel")
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Novel {
    String id;
    Set<String> categoryId;
    Set<String> categoryName;
    String description;
    String bookName;
    String authorId;
    String authorName;
    double score;
    String status;
    Long visitCount;
    Long wordCount;
    String lastChapterId;
    String lastChapterName;
    Long lastChapterUpdateTime;
    String image;
}