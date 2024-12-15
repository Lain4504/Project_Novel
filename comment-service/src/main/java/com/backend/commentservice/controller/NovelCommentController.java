package com.backend.commentservice.controller;

import com.backend.commentservice.entity.NovelComment;
import com.backend.commentservice.entity.NovelCommentReply;
import com.backend.commentservice.service.NovelCommentService;
import com.backend.commentservice.service.PostCommentService;
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

    @GetMapping("/{postId}")
    public ResponseEntity<List<NovelComment>> getAllComments(@PathVariable String postId) {
        return ResponseEntity.ok(novelCommentService.getAllComments(postId));
    }

    @PostMapping
    public ResponseEntity<NovelComment> createComment(@RequestBody NovelComment novelComment) {
        return ResponseEntity.ok(novelCommentService.createComment(novelComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NovelComment> updateComment(@PathVariable String id, @RequestBody NovelComment novelComment) {
        return ResponseEntity.ok(novelCommentService.updateComment(id, novelComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        novelCommentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/replies")
    public ResponseEntity<NovelCommentReply> createReply(@RequestBody NovelCommentReply novelCommentReply) {
        return ResponseEntity.ok(novelCommentService.createReply(novelCommentReply));
    }

    @PutMapping("/replies/{id}")
    public ResponseEntity<NovelCommentReply> updateReply(@PathVariable String id, @RequestBody NovelCommentReply novelCommentReply) {
        return ResponseEntity.ok(novelCommentService.updateReply(id, novelCommentReply));
    }

    @DeleteMapping("/replies/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable String id) {
        novelCommentService.deleteReply(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/replies/{commentId}")
    public ResponseEntity<List<NovelCommentReply>> getAllRepliesByCommentId(@PathVariable String commentId) {
        return ResponseEntity.ok(novelCommentService.getAllRepliesByCommentId(commentId));
    }
}
