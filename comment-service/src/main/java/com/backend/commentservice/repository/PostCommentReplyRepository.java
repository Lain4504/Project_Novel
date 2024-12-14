package com.backend.commentservice.repository;

import com.backend.commentservice.entity.PostCommentReply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentReplyRepository extends MongoRepository<PostCommentReply, String> {
    List<PostCommentReply> findAllByCommentId(String commentId);
    List<PostCommentReply> findAllByParentReplyId(String parentReplyId);}
