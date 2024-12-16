package com.backend.commentservice.service;

import com.backend.commentservice.entity.NovelChapterComment;
import com.backend.commentservice.entity.NovelChapterCommentReply;
import com.backend.commentservice.repository.NovelChapterCommentReplyRepository;
import com.backend.commentservice.repository.NovelChapterCommentRepository;
import com.backend.commentservice.repository.httpclient.UserProfileClient;
import com.backend.commentservice.repository.httpclient.UserProfileResponse;
import com.backend.event.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterCommentService {
    NovelChapterCommentRepository novelChapterCommentRepository;
    NovelChapterCommentReplyRepository novelChapterCommentReplyRepository;
    KafkaTemplate<String, Object> kafkaTemplate;
    UserProfileClient userProfileClient;
    public List<NovelChapterComment> getAllComments(String chapterId) {
        return novelChapterCommentRepository.findAllByChapterId(chapterId).stream()
                .map(this::enrichCommentWithUserProfile)
                .collect(Collectors.toList());
    }
    public NovelChapterComment createComment(NovelChapterComment novelChapterComment) {
        novelChapterComment.setCreatedDate(LocalDateTime.now());
        novelChapterComment.setUpdateDateTime(LocalDateTime.now());
        novelChapterComment.setUsername(userProfileClient.getUserProfile(novelChapterComment.getUserId()).getUsername());
        if (!novelChapterComment.getOwnerId().equals(novelChapterComment.getUserId())) {
            NotificationEvent event = NotificationEvent
                    .builder()
                    .channel("NOVEL_CHAPTER")
                    .recipient(novelChapterComment.getOwnerId())
                    .templateCode("NOVEL_CHAPTER_COMMENT_OWNER_NOTIFICATION")
                    .param(Map.of("fromUser", novelChapterComment.getUsername(), "inLocation", novelChapterComment.getChapterName(),
                            "content", novelChapterComment.getContent()))
                    .build();
            //Publish message to kafka
            kafkaTemplate.send("comment-notification", event);
        }
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
        novelCommentReply.setUsername(userProfileClient.getUserProfile(novelCommentReply.getUserId()).getUsername());
        if (!novelCommentReply.getUserOfReplyTo().equals(novelCommentReply.getUserId())) {
            NotificationEvent event = NotificationEvent
                    .builder()
                    .channel("NOVEL_CHAPTER")
                    .recipient(novelCommentReply.getUserOfReplyTo())
                    .templateCode("NOVEL_CHAPTER_COMMENT_REPLY_NOTIFICATION")
                    .param(Map.of("fromUser", novelCommentReply.getUsername(), "inLocation", novelCommentReply.getChapterName(),
                            "content", novelCommentReply.getReplyContent()))
                    .build();
            //Publish message to kafka
            kafkaTemplate.send("comment-notification", event);
        }
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
