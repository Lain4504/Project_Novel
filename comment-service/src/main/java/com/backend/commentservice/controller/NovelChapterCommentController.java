package com.backend.commentservice.controller;

import com.backend.commentservice.entity.NovelChapterComment;
import com.backend.commentservice.entity.NovelChapterCommentReply;
import com.backend.commentservice.service.NovelChapterCommentService;
import com.backend.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novel-chapter-comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterCommentController {
    NovelChapterCommentService novelChapterCommentService;

    @GetMapping("/{chapterId}")
    public ApiResponse<List<NovelChapterComment>> getAllComments(@PathVariable String chapterId) {
        return ApiResponse.<List<NovelChapterComment>>builder()
                .result(novelChapterCommentService.getAllComments(chapterId))
                .build();
    }

    @PostMapping
    public ApiResponse<NovelChapterComment> createComment(@RequestBody NovelChapterComment novelChapterComment) {
        return ApiResponse.<NovelChapterComment>builder()
                .result(novelChapterCommentService.createComment(novelChapterComment))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NovelChapterComment> updateComment(@PathVariable String id, @RequestBody NovelChapterComment novelChapterComment) {
        return ApiResponse.<NovelChapterComment>builder()
                .result(novelChapterCommentService.updateComment(id, novelChapterComment))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable String id) {
        novelChapterCommentService.deleteComment(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/replies")
    public ApiResponse<NovelChapterCommentReply> createReply(@RequestBody NovelChapterCommentReply novelChapterCommentReply) {
        return ApiResponse.<NovelChapterCommentReply>builder()
                .result(novelChapterCommentService.createReply(novelChapterCommentReply))
                .build();
    }

    @PutMapping("/replies/{id}")
    public ApiResponse<NovelChapterCommentReply> updateReply(@PathVariable String id, @RequestBody NovelChapterCommentReply novelChapterCommentReply) {
        return ApiResponse.<NovelChapterCommentReply>builder()
                .result(novelChapterCommentService.updateReply(id, novelChapterCommentReply))
                .build();
    }

    @DeleteMapping("/replies/{id}")
    public ApiResponse<Void> deleteReply(@PathVariable String id) {
        novelChapterCommentService.deleteReply(id);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/replies/{commentId}")
    public ApiResponse<List<NovelChapterCommentReply>> getAllRepliesByCommentId(@PathVariable String commentId) {
        return ApiResponse.<List<NovelChapterCommentReply>>builder()
                .result(novelChapterCommentService.getAllRepliesByCommentId(commentId))
                .build();
    }
}
