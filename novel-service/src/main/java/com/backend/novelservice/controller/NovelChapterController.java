package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelChapterRequest;
import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.response.NovelChapterResponse;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.service.NovelChapterService;
import com.backend.novelservice.service.NovelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novel-chapters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterController {
    NovelChapterService novelChapterService;
    @PostMapping("/create")
    ApiResponse<NovelChapterResponse> createChapter(@RequestBody NovelChapterRequest request) {
        return ApiResponse.<NovelChapterResponse>builder()
                .result(novelChapterService.createChapter(request)).build();
    }
    @PutMapping("/update/{chapterId}")
    ApiResponse<NovelChapterResponse> updateChapter(@PathVariable("chapterId") String chapterId, @RequestBody NovelChapterRequest request) {
        return ApiResponse.<NovelChapterResponse>builder()
                .result(novelChapterService.updateChapter(chapterId, request)).build();
    }
    @DeleteMapping("/delete/{chapterId}")
    ApiResponse<Void> deleteChapter(@PathVariable("chapterId") String chapterId) {
        novelChapterService.deleteChapter(chapterId);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/{chapterId}")
    ApiResponse<NovelChapterResponse> getChapter(@PathVariable("chapterId") String chapterId) {
        return ApiResponse.<NovelChapterResponse>builder()
                .result(novelChapterService.getChapter(chapterId)).build();
    }
    @GetMapping("/get-all/")
    ApiResponse<PageResponse<NovelChapterResponse>> myPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ApiResponse.<PageResponse<NovelChapterResponse>>builder()
                .result(novelChapterService.getChapters(page, size)).build();
    }



}
