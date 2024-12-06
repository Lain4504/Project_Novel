package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.request.NovelUpdateRequest;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.service.NovelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelController {
    NovelService novelService;

    @PostMapping("/create")
    ApiResponse<NovelResponse> createNovel(@RequestBody NovelCreationRequest request) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.createNovel(request)).build();
    }
    @PutMapping("/update/{novelId}")
    ApiResponse<NovelResponse> updateNovel(@PathVariable("novelId") String novelId, @RequestBody NovelUpdateRequest request) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.updateNovel(novelId, request)).build();
    }
    @DeleteMapping("/delete/{novelId}")
    ApiResponse<Void> deleteNovel(@PathVariable("novelId") String novelId) {
        novelService.deleteNovel(novelId);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/{novelId}")
    ApiResponse<NovelResponse> getNovel(@PathVariable("novelId") String novelId) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.getNovel(novelId)).build();
    }
    @GetMapping("/get-all/")
    ApiResponse<PageResponse<NovelResponse>> myPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovels(page, size)).build();
    }
}
