package com.backend.commentservice.controller;

import com.backend.commentservice.entity.NovelComment;
import com.backend.commentservice.entity.NovelCommentReply;
import com.backend.commentservice.service.NovelCommentService;
import com.backend.dto.response.ApiResponse;
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
    public ApiResponse<List<NovelComment>> getAllComments(@PathVariable String novelId) {
        return ApiResponse.<List<NovelComment>>builder()
                .result(novelCommentService.getAllComments(novelId))
                .build();
    }

    @PostMapping
    public ApiResponse<NovelComment> createComment(@RequestBody NovelComment novelComment) {
        return ApiResponse.<NovelComment>builder()
                .result(novelCommentService.createComment(novelComment))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NovelComment> updateComment(@PathVariable String id, @RequestBody NovelComment novelComment) {
        return ApiResponse.<NovelComment>builder()
                .result(novelCommentService.updateComment(id, novelComment))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable String id) {
        novelCommentService.deleteComment(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/replies")
    public ApiResponse<NovelCommentReply> createReply(@RequestBody NovelCommentReply novelCommentReply) {
        return ApiResponse.<NovelCommentReply>builder()
                .result(novelCommentService.createReply(novelCommentReply))
                .build();
    }

    @PutMapping("/replies/{id}")
    public ApiResponse<NovelCommentReply> updateReply(@PathVariable String id, @RequestBody NovelCommentReply novelCommentReply) {
        return ApiResponse.<NovelCommentReply>builder()
                .result(novelCommentService.updateReply(id, novelCommentReply))
                .build();
    }

    @DeleteMapping("/replies/{id}")
    public ApiResponse<Void> deleteReply(@PathVariable String id) {
        novelCommentService.deleteReply(id);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/replies/{commentId}")
    public ApiResponse<List<NovelCommentReply>> getAllRepliesByCommentId(@PathVariable String commentId) {
        return ApiResponse.<List<NovelCommentReply>>builder()
                .result(novelCommentService.getAllRepliesByCommentId(commentId))
                .build();
    }
}
