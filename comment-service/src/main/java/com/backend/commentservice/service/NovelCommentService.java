package com.backend.commentservice.service;

import com.backend.commentservice.entity.NovelComment;
import com.backend.commentservice.entity.NovelCommentReply;
import com.backend.commentservice.entity.NovelComment;
import com.backend.commentservice.entity.NovelCommentReply;
import com.backend.commentservice.repository.NovelCommentReplyRepository;
import com.backend.commentservice.repository.NovelCommentRepository;
import com.backend.commentservice.repository.PostCommentReplyRepository;
import com.backend.commentservice.repository.PostCommentRepository;
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
public class NovelCommentService {
    NovelCommentRepository novelCommentRepository;
    NovelCommentReplyRepository novelCommentReplyRepository;
    UserProfileClient userProfileClient;
    public List<NovelComment> getAllComments(String novelId) {
        return novelCommentRepository.findAllByNovelId(novelId).stream()
                .map(this::enrichCommentWithUserProfile)
                .collect(Collectors.toList());
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
        return novelCommentReplyRepository.findAllByCommentId(commentId).stream()
                .map(this::enrichReplyWithUserProfile)
                .collect(Collectors.toList());
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
     NovelComment enrichCommentWithUserProfile(NovelComment comment) {
        UserProfileResponse userProfile = userProfileClient.getUserProfile(comment.getUserId());
        comment.setUsername(userProfile.getUsername());
        if (userProfile.getImage() != null) {
            comment.setUserAvatar(userProfile.getImage().getPath());
        } else {
            comment.setUserAvatar(null); // or set a default value if needed
        }
        return comment;
    }

     NovelCommentReply enrichReplyWithUserProfile(NovelCommentReply reply) {
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
