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
    ApiResponse<NovelChapterResponse> createChapter( @PathVariable("volumeId") String volumeId,  @RequestBody NovelChapterRequest request) {
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
    @GetMapping("/{volumeId}/chapters")
    ApiResponse<List<NovelChapterResponse>> getChaptersByVolumeId(
            @PathVariable("volumeId") String volumeId
           ){
        return ApiResponse.<List<NovelChapterResponse>>builder()
                .result(novelChapterService.getChaptersByVolumeId(volumeId)).build();
    }
    @GetMapping("/{volumeId}/{chapterNumber}")
    public ApiResponse<NovelChapter> getChapter(@PathVariable String volumeId, @PathVariable Integer chapterNumber) {
        Optional<NovelChapter> chapter = novelChapterService.getChapterByNumber(volumeId, chapterNumber);
        return ApiResponse.<NovelChapter>builder()
                .result(chapter.orElse(null)).build();
    }

    // API lấy chapter trước đó
    @GetMapping("/{volumeId}/{chapterNumber}/previous")
    public ApiResponse<NovelChapter> getPreviousChapter(@PathVariable String volumeId, @PathVariable Integer chapterNumber) {
        Optional<NovelChapter> previousChapter = novelChapterService.getPreviousChapter(volumeId, chapterNumber);
        return ApiResponse.<NovelChapter>builder()
                .result(previousChapter.orElse(null)).build();
    }

    // API lấy chapter sau đó
    @GetMapping("/{volumeId}/{chapterNumber}/next")
    public ApiResponse<NovelChapter> getNextChapter(@PathVariable String volumeId, @PathVariable Integer chapterNumber) {
        Optional<NovelChapter> nextChapter = novelChapterService.getNextChapter(volumeId, chapterNumber);
        return ApiResponse.<NovelChapter>builder()
                .result(nextChapter.orElse(null)).build();
    }

}
