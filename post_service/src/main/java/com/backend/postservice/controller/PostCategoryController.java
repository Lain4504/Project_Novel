package com.backend.postservice.controller;

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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostCategoryController {
    PostCategoryService postCategoryService;
    @PostMapping("/create")
    public ResponseEntity<PostCategoryResponse> createPostCategory(@RequestBody PostCategoryRequest request){
        return ResponseEntity.ok(postCategoryService.createPostCategory(request));
    }
    @PutMapping("/update/{postCategoryId}")
    public ResponseEntity<PostCategoryResponse> updatePostCategory(@PathVariable("postCategoryId") String postCategory, @RequestBody PostCategoryRequest request){
        return ResponseEntity.ok(postCategoryService.updatePostCategory(postCategory, request));
    }
    @GetMapping("/all")
    public ResponseEntity<List<PostCategoryResponse>> getAllPostCategories(){
        return ResponseEntity.ok(postCategoryService.getAllPostCategories());
    }
    @GetMapping("/{postCategoryId}")
    public ResponseEntity<PostCategoryResponse> getPostCategory(@PathVariable("postCategoryId") String postCategoryId){
        return ResponseEntity.ok(postCategoryService.getPostCategory(postCategoryId));
    }
    @DeleteMapping("/{postCategoryId}")
    public ResponseEntity<Void> deletePostCategory(@PathVariable("postCategoryId") String postCategoryId){
        postCategoryService.deletePostCategory(postCategoryId);
        return ResponseEntity.ok().build();
    }
}
