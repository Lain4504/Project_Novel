package com.backend.commentservice.controller;
import com.backend.commentservice.entity.PostComment;
import com.backend.commentservice.entity.PostCommentReply;
import com.backend.commentservice.service.PostCommentService;
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
    public ResponseEntity<List<PostComment>> getAllComments(@PathVariable String postId) {
        return ResponseEntity.ok(postCommentService.getAllComments(postId));
    }

    @PostMapping
    public ResponseEntity<PostComment> createComment(@RequestBody PostComment postComment) {
        return ResponseEntity.ok(postCommentService.createComment(postComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostComment> updateComment(@PathVariable String id, @RequestBody PostComment postComment) {
        return ResponseEntity.ok(postCommentService.updateComment(id, postComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        postCommentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/replies/{commentId}")
    public ResponseEntity<List<PostCommentReply>> getAllRepliesByCommentId(@PathVariable String commentId) {
        return ResponseEntity.ok(postCommentService.getAllRepliesByCommentId(commentId));
    }

    @GetMapping("/replies/parent/{parentReplyId}")
    public ResponseEntity<List<PostCommentReply>> getAllRepliesByParentReplyId(@PathVariable String parentReplyId) {
        return ResponseEntity.ok(postCommentService.getAllRepliesByParentReplyId(parentReplyId));
    }

    @PostMapping("/replies")
    public ResponseEntity<PostCommentReply> createReply(@RequestBody PostCommentReply postCommentReply) {
        return ResponseEntity.ok(postCommentService.createReply(postCommentReply));
    }

    @PutMapping("/replies/{id}")
    public ResponseEntity<PostCommentReply> updateReply(@PathVariable String id, @RequestBody PostCommentReply postCommentReply) {
        return ResponseEntity.ok(postCommentService.updateReply(id, postCommentReply));
    }

    @DeleteMapping("/replies/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable String id) {
        postCommentService.deleteReply(id);
        return ResponseEntity.ok().build();
    }
}