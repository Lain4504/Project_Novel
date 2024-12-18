package com.backend.commentservice.controller;
import com.backend.commentservice.dto.request.PostCommentReplyRequest;
import com.backend.commentservice.dto.request.PostCommentRequest;
import com.backend.commentservice.dto.response.PostCommentReplyResponse;
import com.backend.commentservice.dto.response.PostCommentResponse;
import com.backend.commentservice.entity.PostComment;
import com.backend.commentservice.entity.PostCommentReply;
import com.backend.commentservice.service.PostCommentService;
import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import jakarta.validation.Valid;
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
    public ApiResponse<PageResponse<PostCommentResponse>> getAllComments(
            @PathVariable("postId") String postId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
         ) {
        return ApiResponse.<PageResponse<PostCommentResponse>>builder()
                .result(postCommentService.getAllComments(postId, page, size))
                .build();
    }

    @PostMapping
    public ApiResponse<PostCommentResponse> createComment(@Valid @RequestBody PostCommentRequest request) {
        return ApiResponse.<PostCommentResponse>builder()
                .result(postCommentService.createComment(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PostCommentResponse> updateComment(@PathVariable String id, @Valid @RequestBody PostCommentRequest request) {
        return ApiResponse.<PostCommentResponse>builder()
                .result(postCommentService.updateComment(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable String id) {
        postCommentService.deleteComment(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/replies/{commentId}")
    public ApiResponse<PageResponse<PostCommentReplyResponse>> getAllRepliesByCommentId(
            @PathVariable String commentId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size

    ) {
        return ApiResponse.<PageResponse<PostCommentReplyResponse>>builder()
                .result(postCommentService.getAllRepliesByCommentId(commentId, page, size))
                .build();
    }
    @PostMapping("/replies")
    public ApiResponse<PostCommentReplyResponse> createReply(@Valid @RequestBody PostCommentReplyRequest request) {
        return ApiResponse.<PostCommentReplyResponse>builder()
                .result(postCommentService.createReply(request))
                .build();
    }

    @PutMapping("/replies/{id}")
    public ApiResponse<PostCommentReplyResponse> updateReply(@PathVariable String id, @Valid @RequestBody PostCommentReplyRequest request) {
        return ApiResponse.<PostCommentReplyResponse>builder()
                .result(postCommentService.updateReply(id, request))
                .build();
    }

    @DeleteMapping("/replies/{id}")
    public ApiResponse<Void> deleteReply(@PathVariable String id) {
        postCommentService.deleteReply(id);
        return ApiResponse.<Void>builder().build();
    }
}