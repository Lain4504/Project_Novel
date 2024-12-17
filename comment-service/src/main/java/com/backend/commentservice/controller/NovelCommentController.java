package com.backend.commentservice.controller;

import com.backend.commentservice.dto.request.NovelCommentReplyRequest;
import com.backend.commentservice.dto.request.NovelCommentRequest;
import com.backend.commentservice.dto.response.NovelCommentReplyResponse;
import com.backend.commentservice.dto.response.NovelCommentResponse;
import com.backend.commentservice.entity.NovelComment;
import com.backend.commentservice.entity.NovelCommentReply;
import com.backend.commentservice.service.NovelCommentService;
import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novel-comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelCommentController {
    NovelCommentService novelCommentService;

    @GetMapping("/{novelId}")
    public ApiResponse<PageResponse<NovelCommentResponse>> getAllComments(
            @PathVariable String novelId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelCommentResponse>>builder()
                .result(novelCommentService.getAllComments(novelId, page, size))
                .build();
    }

    @PostMapping
    public ApiResponse<NovelCommentResponse> createComment(@RequestBody NovelCommentRequest request) {
        return ApiResponse.<NovelCommentResponse>builder()
                .result(novelCommentService.createComment(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NovelCommentResponse> updateComment(@PathVariable String id, @RequestBody NovelCommentRequest request) {
        return ApiResponse.<NovelCommentResponse>builder()
                .result(novelCommentService.updateComment(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable String id) {
        novelCommentService.deleteComment(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/replies")
    public ApiResponse<NovelCommentReplyResponse> createReply(@RequestBody NovelCommentReplyRequest request) {
        return ApiResponse.<NovelCommentReplyResponse>builder()
                .result(novelCommentService.createReply(request))
                .build();
    }

    @PutMapping("/replies/{id}")
    public ApiResponse<NovelCommentReplyResponse> updateReply(@PathVariable String id, @RequestBody NovelCommentReplyRequest request) {
        return ApiResponse.<NovelCommentReplyResponse>builder()
                .result(novelCommentService.updateReply(id, request))
                .build();
    }

    @DeleteMapping("/replies/{id}")
    public ApiResponse<Void> deleteReply(@PathVariable String id) {
        novelCommentService.deleteReply(id);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/replies/{commentId}")
    public ApiResponse<PageResponse<NovelCommentReplyResponse>> getAllRepliesByCommentId(
            @PathVariable String commentId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ) {
        return ApiResponse.<PageResponse<NovelCommentReplyResponse>>builder()
                .result(novelCommentService.getAllRepliesByCommentId(commentId, page, size))
                .build();
    }
}
