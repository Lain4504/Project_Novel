package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelCategoryRequest;
import com.backend.novelservice.dto.response.NovelCategoryResponse;
import com.backend.novelservice.service.NovelCategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novel-categories")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NovelCategoryController {
    NovelCategoryService novelCategoryService;
    @PostMapping("/create")
    ApiResponse<NovelCategoryResponse> createCategory(@RequestBody NovelCategoryRequest request) {
        return ApiResponse.<NovelCategoryResponse>builder()
                .result(novelCategoryService.createNovelCategory(request)).build();
    }
    @GetMapping("/all")
    ApiResponse<PageResponse<NovelCategoryResponse>> getAllCategories(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelCategoryResponse>>builder()
                .result(novelCategoryService.getNovelCategories(page, size)).build();
    }
    @GetMapping("/{categoryId}")
    ApiResponse<NovelCategoryResponse> getCategory(String categoryId) {
        return ApiResponse.<NovelCategoryResponse>builder()
                .result(novelCategoryService.getNovelCategoryById(categoryId)).build();
    }
    @DeleteMapping("/delete/{categoryId}")
    ApiResponse<Void> deleteCategory(String categoryId) {
        novelCategoryService.deleteNovelCategory(categoryId);
        return ApiResponse.<Void>builder().build();
    }
    @PutMapping("/update/{categoryId}")
    ApiResponse<NovelCategoryResponse> updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody NovelCategoryRequest request) {
        return ApiResponse.<NovelCategoryResponse>builder()
                .result(novelCategoryService.updateNovelCategory(categoryId, request)).build();
    }
}
