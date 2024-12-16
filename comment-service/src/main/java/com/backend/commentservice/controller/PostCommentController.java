package com.backend.commentservice.controller;
import com.backend.commentservice.entity.PostComment;
import com.backend.commentservice.entity.PostCommentReply;
import com.backend.commentservice.service.PostCommentService;
import com.backend.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/post-comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostCommentController {
    PostCommentService postCommentService;

    @GetMapping("/{postId}")
    public ApiResponse<List<PostComment>> getAllComments(@PathVariable String postId) {
        return ApiResponse.<List<PostComment>>builder()
                .result(postCommentService.getAllComments(postId))
                .build();
    }

    @PostMapping
    public ApiResponse<PostComment> createComment(@RequestBody PostComment postComment) {
        return ApiResponse.<PostComment>builder()
                .result(postCommentService.createComment(postComment))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PostComment> updateComment(@PathVariable String id, @RequestBody PostComment postComment) {
        return ApiResponse.<PostComment>builder()
                .result(postCommentService.updateComment(id, postComment))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable String id) {
        postCommentService.deleteComment(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/replies/{commentId}")
    public ApiResponse<List<PostCommentReply>> getAllRepliesByCommentId(@PathVariable String commentId) {
        return ApiResponse.<List<PostCommentReply>>builder()
                .result(postCommentService.getAllRepliesByCommentId(commentId))
                .build();
    }
    @PostMapping("/replies")
    public ApiResponse<PostCommentReply> createReply(@RequestBody PostCommentReply postCommentReply) {
        return ApiResponse.<PostCommentReply>builder()
                .result(postCommentService.createReply(postCommentReply))
                .build();
    }

    @PutMapping("/replies/{id}")
    public ApiResponse<PostCommentReply> updateReply(@PathVariable String id, @RequestBody PostCommentReply postCommentReply) {
        return ApiResponse.<PostCommentReply>builder()
                .result(postCommentService.updateReply(id, postCommentReply))
                .build();
    }

    @DeleteMapping("/replies/{id}")
    public ApiResponse<Void> deleteReply(@PathVariable String id) {
        postCommentService.deleteReply(id);
        return ApiResponse.<Void>builder().build();
    }
}