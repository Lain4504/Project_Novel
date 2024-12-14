package com.backend.commentservice.controller;

import com.backend.commentservice.entity.NovelChapterComment;
import com.backend.commentservice.entity.NovelChapterCommentReply;
import com.backend.commentservice.service.NovelChapterCommentService;
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

    @GetMapping("/{postId}")
    public ResponseEntity<List<NovelChapterComment>> getAllComments(@PathVariable String postId) {
        return ResponseEntity.ok(novelChapterCommentService.getAllComments(postId));
    }

    @PostMapping
    public ResponseEntity<NovelChapterComment> createComment(@RequestBody NovelChapterComment novelChapterComment) {
        return ResponseEntity.ok(novelChapterCommentService.createComment(novelChapterComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NovelChapterComment> updateComment(@PathVariable String id, @RequestBody NovelChapterComment novelChapterComment) {
        return ResponseEntity.ok(novelChapterCommentService.updateComment(id, novelChapterComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        novelChapterCommentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/replies")
    public ResponseEntity<NovelChapterCommentReply> createReply(@RequestBody NovelChapterCommentReply novelChapterCommentReply) {
        return ResponseEntity.ok(novelChapterCommentService.createReply(novelChapterCommentReply));
    }

    @PutMapping("/replies/{id}")
    public ResponseEntity<NovelChapterCommentReply> updateReply(@PathVariable String id, @RequestBody NovelChapterCommentReply novelChapterCommentReply) {
        return ResponseEntity.ok(novelChapterCommentService.updateReply(id, novelChapterCommentReply));
    }

    @DeleteMapping("/replies/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable String id) {
        novelChapterCommentService.deleteReply(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/replies/{commentId}")
    public ResponseEntity<List<NovelChapterCommentReply>> getAllRepliesByCommentId(@PathVariable String commentId) {
        return ResponseEntity.ok(novelChapterCommentService.getAllRepliesByCommentId(commentId));
    }
}
