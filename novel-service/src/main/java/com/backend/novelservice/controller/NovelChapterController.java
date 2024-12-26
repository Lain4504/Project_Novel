package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelChapterRequest;
import com.backend.novelservice.dto.response.NovelChapterResponse;
import com.backend.novelservice.entity.NovelChapter;
import com.backend.novelservice.service.NovelChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/novel-chapters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterController {
    NovelChapterService novelChapterService;

    @PostMapping("/create/{volumeId}")
    ApiResponse<NovelChapterResponse> createChapter(@PathVariable("volumeId") String volumeId, @RequestBody NovelChapterRequest request) {
        return ApiResponse.<NovelChapterResponse>builder()
                .result(novelChapterService.createChapter(volumeId, request)).build();
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

    @GetMapping("/get/{chapterId}")
    ApiResponse<NovelChapterResponse> getChapter(@PathVariable("chapterId") String chapterId) {
        return ApiResponse.<NovelChapterResponse>builder()
                .result(novelChapterService.getChapter(chapterId)).build();
    }

    @GetMapping("/get/get-all/")
    ApiResponse<PageResponse<NovelChapterResponse>> getChapters(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<NovelChapterResponse>>builder()
                .result(novelChapterService.getChapters(page, size)).build();
    }

    @GetMapping("/get/{volumeId}/chapters")
    ApiResponse<List<NovelChapterResponse>> getChaptersByVolumeId(
            @PathVariable("volumeId") String volumeId,
            @RequestParam(value = "status", required = false) String status) {
        return ApiResponse.<List<NovelChapterResponse>>builder()
                .result(novelChapterService.getChaptersByVolumeId(volumeId, status)).build();
    }

    @GetMapping("/get/{volumeId}/{chapterNumber}")
    public ApiResponse<NovelChapter> getChapter(@PathVariable String volumeId, @PathVariable Integer chapterNumber) {
        Optional<NovelChapter> chapter = novelChapterService.getChapterByNumber(volumeId, chapterNumber);
        return ApiResponse.<NovelChapter>builder()
                .result(chapter.orElse(null)).build();
    }

    @GetMapping("/get/{volumeId}/{chapterNumber}/previous")
    public ApiResponse<NovelChapterResponse> getPreviousChapter(@PathVariable String volumeId, @PathVariable Integer chapterNumber) {
        NovelChapterResponse previousChapter = novelChapterService.getPreviousChapter(volumeId, chapterNumber);
        return ApiResponse.<NovelChapterResponse>builder()
                .result(previousChapter).build();
    }

    @GetMapping("/get/{volumeId}/{chapterNumber}/next")
    public ApiResponse<NovelChapterResponse> getNextChapter(@PathVariable String volumeId, @PathVariable Integer chapterNumber) {
        NovelChapterResponse nextChapter = novelChapterService.getNextChapter(volumeId, chapterNumber);
        return ApiResponse.<NovelChapterResponse>builder()
                .result(nextChapter).build();
    }

    @PutMapping("/increment-visit-count/{chapterId}")
    public ApiResponse<Void> incrementVisitCount(@PathVariable String chapterId) {
        novelChapterService.incrementVisitCount(chapterId);
        return ApiResponse.<Void>builder().build();
    }
    @PutMapping("/reoder-chapter/{volumeId}")
    public ApiResponse<Void> reorderChapter(@PathVariable String volumeId, @RequestBody List<String> chapterIds) {
        novelChapterService.reorderChapters(volumeId, chapterIds);
        return ApiResponse.<Void>builder().build();
    }
}
