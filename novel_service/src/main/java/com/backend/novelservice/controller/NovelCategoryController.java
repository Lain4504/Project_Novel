package com.backend.novelservice.controller;

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
    ResponseEntity<NovelCategoryResponse> createCategory(@RequestBody NovelCategoryRequest request) {
        return ResponseEntity.ok(novelCategoryService.createNovelCategory(request));
    }
    @GetMapping("/all")
    ResponseEntity<List<NovelCategoryResponse>> getAllCategories(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(novelCategoryService.getNovelCategories(page, size));
    }
    @GetMapping("/{categoryId}")
    ResponseEntity<NovelCategoryResponse> getCategory(String categoryId) {
        return ResponseEntity.ok(novelCategoryService.getNovelCategoryById(categoryId));
    }
    @DeleteMapping("/delete/{categoryId}")
    ResponseEntity<Void> deleteCategory(String categoryId) {
        novelCategoryService.deleteNovelCategory(categoryId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{categoryId}")
    ResponseEntity<NovelCategoryResponse> updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody NovelCategoryRequest request) {
        return ResponseEntity.ok(novelCategoryService.updateNovelCategory(categoryId, request));
    }
}
