package com.backend.commentservice.service;

import com.backend.commentservice.entity.PostComment;
import com.backend.commentservice.entity.PostCommentReply;
import com.backend.commentservice.repository.PostCommentReplyRepository;
import com.backend.commentservice.repository.PostCommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostCommentService {
    PostCommentReplyRepository postCommentReplyRepository;
    PostCommentRepository postCommentRepository;
    public List<PostComment> getAllComments(String postId) {
        return postCommentRepository.findAllByPostId(postId);
    }
    public PostComment createComment(PostComment postComment) {
        postComment.setCreatedDate(LocalDateTime.now());
        postComment.setUpdateDateTime(LocalDateTime.now());
        return postCommentRepository.save(postComment);
    }
    public PostComment updateComment(String id, PostComment postComment) {
        return postCommentRepository.findById(id)
                .map(existingComment -> {
                    existingComment.setContent(postComment.getContent());
                    existingComment.setUpdateDateTime(LocalDateTime.now());
                    return postCommentRepository.save(existingComment);
                })
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
    public void deleteComment(String id) {
        postCommentRepository.deleteById(id);
    }
    public List<PostCommentReply> getAllRepliesByCommentId(String commentId) {
        return postCommentReplyRepository.findAllByCommentId(commentId);
    }
    public List<PostCommentReply> getAllRepliesByParentReplyId(String parentReplyId) {
        return postCommentReplyRepository.findAllByParentReplyId(parentReplyId);
    }
    public PostCommentReply createReply(PostCommentReply postCommentReply) {
        postCommentReply.setCreatedDate(LocalDateTime.now());
        postCommentReply.setUpdateDateTime(LocalDateTime.now());
        return postCommentReplyRepository.save(postCommentReply);
    }
    public PostCommentReply updateReply(String id, PostCommentReply postCommentReply) {
        return postCommentReplyRepository.findById(id)
                .map(existingReply -> {
                    existingReply.setReplyContent(postCommentReply.getReplyContent());
                    existingReply.setUpdateDateTime(LocalDateTime.now());
                    return postCommentReplyRepository.save(existingReply);
                })
                .orElseThrow(() -> new RuntimeException("Reply not found"));
    }

    public void deleteReply(String id) {
        postCommentReplyRepository.deleteById(id);
    }
}
