package com.backend.postservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.postservice.dto.request.PostCategoryRequest;
import com.backend.postservice.dto.response.PostCategoryResponse;
import com.backend.postservice.service.PostCategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post-categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostCategoryController {
    PostCategoryService postCategoryService;
    @PostMapping("/create")
    public ApiResponse<PostCategoryResponse> createPostCategory(@RequestBody PostCategoryRequest request){
        return ApiResponse.<PostCategoryResponse>builder()
                .result(postCategoryService.createPostCategory(request))
                .build();
    }
    @PutMapping("/update/{postCategoryId}")
    public ApiResponse<PostCategoryResponse> updatePostCategory(@PathVariable("postCategoryId") String postCategory, @RequestBody PostCategoryRequest request){
        return ApiResponse.<PostCategoryResponse>builder()
                .result(postCategoryService.updatePostCategory(postCategory, request))
                .build();
    }
    @GetMapping("/all")
    public ApiResponse<List<PostCategoryResponse>> getAllPostCategories(){
        return ApiResponse.<List<PostCategoryResponse>>builder()
                .result(postCategoryService.getAllPostCategories())
                .build();
    }
    @GetMapping("/{postCategoryId}")
    public ApiResponse<PostCategoryResponse> getPostCategory(@PathVariable("postCategoryId") String postCategoryId){
        return ApiResponse.<PostCategoryResponse>builder()
                .result(postCategoryService.getPostCategory(postCategoryId))
                .build();
    }
    @DeleteMapping("/delete/{postCategoryId}")
    public ApiResponse<Void> deletePostCategory(@PathVariable("postCategoryId") String postCategoryId){
        postCategoryService.deletePostCategory(postCategoryId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
