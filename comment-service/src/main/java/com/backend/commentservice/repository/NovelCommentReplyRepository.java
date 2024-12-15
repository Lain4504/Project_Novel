package com.backend.commentservice.repository;

import com.backend.commentservice.entity.NovelCommentReply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelCommentReplyRepository extends MongoRepository<NovelCommentReply, String> {
    List<NovelCommentReply> findAllByCommentId(String commentId);
}