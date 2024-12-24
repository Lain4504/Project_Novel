package com.backend.postservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.postservice.dto.request.PostRequest;
import com.backend.postservice.dto.response.PostResponse;
import com.backend.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/posts")
public class PostController {
    PostService postService;

    @PostMapping("/create")
    ApiResponse<PostResponse> createPost(@Valid @RequestBody PostRequest request){
        return ApiResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }

    @GetMapping("/my-posts")
    ApiResponse<PageResponse<PostResponse>> myPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .result(postService.getMyPosts(page, size))
                .build();
    }
    @PutMapping("/update/{postId}")
    ApiResponse<PostResponse> updatePost(@PathVariable("postId") String postId, @Valid  @RequestBody PostRequest request){
        return ApiResponse.<PostResponse>builder()
                .result(postService.updatePost(postId, request))
                .build();
    }
    @GetMapping("/get/{postId}")
    ApiResponse<PostResponse> getPost(@PathVariable("postId") String postId){
        return ApiResponse.<PostResponse>builder()
                .result(postService.getPost(postId))
                .build();
    }
    @GetMapping("/get/all")
    ApiResponse<PageResponse<PostResponse>> getAllPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "categoryId", required = false) String categoryId) {
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .result(postService.getAllPosts(page, size, categoryId))
                .build();
    }
    @DeleteMapping("/delete/{postId}")
    ApiResponse<Void> deletePost(@PathVariable("postId") String postId){
        postService.deletePost(postId);
        return ApiResponse.<Void>builder().build();
    }
}