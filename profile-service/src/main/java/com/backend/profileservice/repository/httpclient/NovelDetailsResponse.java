package com.backend.profileservice.repository.httpclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovelDetailsResponse {
    private String novelId;
    private String novelName;
    private String author;
    private int chapterCount;
}

