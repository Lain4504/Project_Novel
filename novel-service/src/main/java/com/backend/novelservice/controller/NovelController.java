package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.request.NovelUpdateRequest;
import com.backend.novelservice.dto.response.NovelDetailsResponse;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.service.NovelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/novels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelController {
    NovelService novelService;

    @PostMapping("/create")
    ApiResponse<NovelResponse> createNovel(@RequestPart("novel") NovelCreationRequest request, @RequestPart("image") MultipartFile imageFile) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.createNovel(request, imageFile)).build();
    }

    @PutMapping("/update/{novelId}")
    ApiResponse<NovelResponse> updateNovel(@PathVariable("novelId") String novelId, @RequestPart("novel") NovelUpdateRequest request, @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.updateNovel(novelId, request, imageFile)).build();
    }

    @DeleteMapping("/delete/{novelId}")
    ApiResponse<Void> deleteNovel(@PathVariable("novelId") String novelId) {
        novelService.deleteNovel(novelId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/get/{novelId}")
    ApiResponse<NovelResponse> getNovel(@PathVariable("novelId") String novelId) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.getNovel(novelId)).build();
    }

    @GetMapping("/get/all")
    ApiResponse<PageResponse<NovelResponse>> getAllNovels(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovels(page, size)).build();
    }

    @GetMapping("/my-novels")
    ApiResponse<PageResponse<NovelResponse>> getMyNovels(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getMyNovels(page, size)).build();
    }

    @GetMapping("/get/latest-novels")
    ApiResponse<PageResponse<NovelResponse>> getLatestNovels(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getLatestNovels(page, size)).build();
    }

    @GetMapping("/get/author/{authorId}")
    ApiResponse<PageResponse<NovelResponse>> getNovelsByAuthor(
            @PathVariable("authorId") String authorId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovelsByAuthor(authorId, page, size)).build();
    }

    @PostMapping("/details")
    public List<NovelDetailsResponse> getNovelDetails(@RequestBody List<String> novelIds) {
        return novelService.getNovelDetails(novelIds);
    }

    @GetMapping("/get/find-by-status/{status}")
    public ApiResponse<PageResponse<NovelResponse>> getNovelsByStatus(
            @PathVariable("status") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovelsByStatus(status, page, size)).build();
    }

    @GetMapping("/get/find-by-fields/{fields}")
    public ApiResponse<PageResponse<NovelResponse>> getNovelsByFields(
            @PathVariable("fields") String fields,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovelsByDynamicField(fields, page, size)).build();
    }
}
