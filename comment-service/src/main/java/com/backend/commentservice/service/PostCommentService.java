package com.backend.commentservice.service;

import com.backend.commentservice.entity.PostComment;
import com.backend.commentservice.entity.PostCommentReply;
import com.backend.commentservice.repository.PostCommentReplyRepository;
import com.backend.commentservice.repository.PostCommentRepository;
import com.backend.commentservice.repository.httpclient.UserProfileClient;
import com.backend.commentservice.repository.httpclient.UserProfileResponse;
import com.backend.event.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostCommentService {
    PostCommentReplyRepository postCommentReplyRepository;
    PostCommentRepository postCommentRepository;
    UserProfileClient userProfileClient;
    KafkaTemplate<String, Object> kafkaTemplate;

    public List<PostComment> getAllComments(String postId) {
        return postCommentRepository.findAllByPostId(postId).stream()
                .map(this::enrichCommentWithUserProfile)
                .collect(Collectors.toList());
    }

    public PostComment createComment(PostComment postComment) {
        postComment.setCreatedDate(LocalDateTime.now());
        postComment.setUpdateDateTime(LocalDateTime.now());
        UserProfileResponse userProfile = userProfileClient.getUserProfile(postComment.getUserId());
        postComment.setUsername(userProfile.getUsername());
        NotificationEvent event = NotificationEvent
                .builder()
                .channel("PUSH_NOTIFICATION")
                .recipient(postComment.getOwnerId())
                .templateCode("COMMENT_OWNER_NOTIFICATION")
                .param(Map.of("fromUser", postComment.getUsername(), "inLocation", postComment.getPostName(),
                        "content", postComment.getContent()))
                .build();
        //Publish message to kafka
        kafkaTemplate.send("post-comment-notification", event);
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
        return postCommentReplyRepository.findAllByCommentId(commentId).stream()
                .map(this::enrichReplyWithUserProfile)
                .collect(Collectors.toList());
    }

    public PostCommentReply createReply(PostCommentReply postCommentReply) {
        postCommentReply.setCreatedDate(LocalDateTime.now());
        postCommentReply.setUpdateDateTime(LocalDateTime.now());
        postCommentReply.setUsername(userProfileClient.getUserProfile(postCommentReply.getUserId()).getUsername());
        NotificationEvent event = NotificationEvent
                .builder()
                .channel("PUSH_NOTIFICATION")
                .recipient(postCommentReply.getUserIdOfReplyTo())
                .templateCode("COMMENT_REPLY_NOTIFICATION")
                .param(Map.of("fromUser", postCommentReply.getUsername(), "inLocation", postCommentReply.getPostName(),
                        "content", postCommentReply.getReplyContent()))
                .build();
        //Publish message to kafka
        kafkaTemplate.send("post-comment-notification", event);
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

    private PostComment enrichCommentWithUserProfile(PostComment comment) {
        UserProfileResponse userProfile = userProfileClient.getUserProfile(comment.getUserId());
        comment.setUsername(userProfile.getUsername());
        if (userProfile.getImage() != null) {
            comment.setUserAvatar(userProfile.getImage().getPath());
        } else {
            comment.setUserAvatar(null); // or set a default value if needed
        }
        return comment;
    }

    private PostCommentReply enrichReplyWithUserProfile(PostCommentReply reply) {
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