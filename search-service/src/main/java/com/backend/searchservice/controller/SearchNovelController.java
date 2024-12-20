package com.backend.searchservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.searchservice.dto.response.SearchResponse;
import com.backend.searchservice.service.SearchService;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SearchNovelController {
    SearchService searchService;
    @GetMapping("/novel")
    ApiResponse<List<SearchResponse>> getNovelsByElaticSearch(@RequestParam String query) {
        List<SearchResponse> results = searchService.searchNovels(query);
        return ApiResponse.<List<SearchResponse>>builder()
                .result(results).build();
    }
}
