package com.backend.commentservice.service;

import com.backend.commentservice.entity.NovelChapterComment;
import com.backend.commentservice.entity.NovelChapterCommentReply;
import com.backend.commentservice.repository.NovelChapterCommentReplyRepository;
import com.backend.commentservice.repository.NovelChapterCommentRepository;
import com.backend.commentservice.repository.httpclient.UserProfileClient;
import com.backend.commentservice.repository.httpclient.UserProfileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterCommentService {
    NovelChapterCommentRepository novelChapterCommentRepository;
    NovelChapterCommentReplyRepository novelChapterCommentReplyRepository;
    UserProfileClient userProfileClient;
    public List<NovelChapterComment> getAllComments(String chapterId) {
        return novelChapterCommentRepository.findAllByChapterId(chapterId).stream()
                .map(this::enrichCommentWithUserProfile)
                .collect(Collectors.toList());
    }
    public NovelChapterComment createComment(NovelChapterComment novelChapterComment) {
        novelChapterComment.setCreatedDate(LocalDateTime.now());
        novelChapterComment.setUpdateDateTime(LocalDateTime.now());
        return novelChapterCommentRepository.save(novelChapterComment);
    }
    public NovelChapterComment updateComment(String id, NovelChapterComment novelChapterComment) {
        return novelChapterCommentRepository.findById(id)
                .map(existingComment -> {
                    existingComment.setContent(novelChapterComment.getContent());
                    existingComment.setUpdateDateTime(LocalDateTime.now());
                    return novelChapterCommentRepository.save(existingComment);
                })
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
    public void deleteComment(String id) {
        novelChapterCommentRepository.deleteById(id);
    }
    public List<NovelChapterCommentReply> getAllRepliesByCommentId(String commentId) {
        return novelChapterCommentReplyRepository.findAllByCommentId(commentId).stream()
                .map(this::enrichReplyWithUserProfile)
                .collect(Collectors.toList());
    }
    public NovelChapterCommentReply createReply(NovelChapterCommentReply novelCommentReply) {
        novelCommentReply.setCreatedDate(LocalDateTime.now());
        novelCommentReply.setUpdateDateTime(LocalDateTime.now());
        return novelChapterCommentReplyRepository.save(novelCommentReply);
    }
    public NovelChapterCommentReply updateReply(String id, NovelChapterCommentReply novelCommentReply) {
        return novelChapterCommentReplyRepository.findById(id)
                .map(existingReply -> {
                    existingReply.setReplyContent(novelCommentReply.getReplyContent());
                    existingReply.setUpdateDateTime(LocalDateTime.now());
                    return novelChapterCommentReplyRepository.save(existingReply);
                })
                .orElseThrow(() -> new RuntimeException("Reply not found"));
    }

    public void deleteReply(String id) {
        novelChapterCommentReplyRepository.deleteById(id);
    }
    
    NovelChapterComment enrichCommentWithUserProfile(NovelChapterComment comment) {
        UserProfileResponse userProfile = userProfileClient.getUserProfile(comment.getUserId());
        comment.setUsername(userProfile.getUsername());
        if (userProfile.getImage() != null) {
            comment.setUserAvatar(userProfile.getImage().getPath());
        } else {
            comment.setUserAvatar(null); // or set a default value if needed
        }
        return comment;
    }

     NovelChapterCommentReply enrichReplyWithUserProfile(NovelChapterCommentReply reply) {
        UserProfileResponse userProfile = userProfileClient.getUserProfile(reply.getUserId());
        reply.setUsername(userProfile.getUsername());
        if (userProfile.getImage() != null) {
            reply.setUserAvatar(userProfile.getImage().getPath());
        } else {
            reply.setUserAvatar(null); // or set a default value if needed
        }
        return reply;
    }
}
