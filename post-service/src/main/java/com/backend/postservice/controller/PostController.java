package com.backend.postservice.controller;

import com.backend.postservice.dto.request.PostRequest;
import com.backend.postservice.dto.response.PageResponse;
import com.backend.postservice.dto.response.PostResponse;
import com.backend.postservice.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping("/create")
    ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request){
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping("/my-posts")
    ResponseEntity<PageResponse<PostResponse>> myPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(postService.getMyPosts(page, size));
    }
    @PutMapping("/update/{postId}")
    ResponseEntity<PostResponse> updatePost(@PathVariable("postId") String postId, @RequestBody PostRequest request){
        return ResponseEntity.ok(postService.updatePost(postId, request));
    }
    @GetMapping("/{postId}")
    ResponseEntity<PostResponse> getPost(@PathVariable("postId") String postId){
        return ResponseEntity.ok(postService.getPost(postId));
    }
    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable("postId") String postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}