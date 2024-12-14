package com.backend.commentservice.service;

import com.backend.commentservice.entity.NovelComment;
import com.backend.commentservice.entity.NovelCommentReply;
import com.backend.commentservice.entity.PostComment;
import com.backend.commentservice.entity.PostCommentReply;
import com.backend.commentservice.repository.NovelCommentReplyRepository;
import com.backend.commentservice.repository.NovelCommentRepository;
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
public class NovelCommentService {
    NovelCommentRepository novelCommentRepository;
    NovelCommentReplyRepository novelCommentReplyRepository;
    public List<NovelComment> getAllComments(String novelId) {
        return novelCommentRepository.findAllByNovelId(novelId);
    }
    public NovelComment createComment(NovelComment novelComment) {
        novelComment.setCreatedDate(LocalDateTime.now());
        novelComment.setUpdateDateTime(LocalDateTime.now());
        return novelCommentRepository.save(novelComment);
    }
    public NovelComment updateComment(String id, NovelComment novelComment) {
        return novelCommentRepository.findById(id)
                .map(existingComment -> {
                    existingComment.setContent(novelComment.getContent());
                    existingComment.setUpdateDateTime(LocalDateTime.now());
                    return novelCommentRepository.save(existingComment);
                })
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
    public void deleteComment(String id) {
        novelCommentRepository.deleteById(id);
    }
    public List<NovelCommentReply> getAllRepliesByCommentId(String commentId) {
        return novelCommentReplyRepository.findAllByCommentId(commentId);
    }
    public NovelCommentReply createReply(NovelCommentReply novelCommentReply) {
        novelCommentReply.setCreatedDate(LocalDateTime.now());
        novelCommentReply.setUpdateDateTime(LocalDateTime.now());
        return novelCommentReplyRepository.save(novelCommentReply);
    }
    public NovelCommentReply updateReply(String id, NovelCommentReply novelCommentReply) {
        return novelCommentReplyRepository.findById(id)
                .map(existingReply -> {
                    existingReply.setReplyContent(novelCommentReply.getReplyContent());
                    existingReply.setUpdateDateTime(LocalDateTime.now());
                    return novelCommentReplyRepository.save(existingReply);
                })
                .orElseThrow(() -> new RuntimeException("Reply not found"));
    }

    public void deleteReply(String id) {
        novelCommentReplyRepository.deleteById(id);
    }
}
