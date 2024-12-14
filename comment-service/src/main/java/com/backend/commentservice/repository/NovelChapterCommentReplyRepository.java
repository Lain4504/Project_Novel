package com.backend.commentservice.repository;

import com.backend.commentservice.entity.NovelChapterCommentReply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelChapterCommentReplyRepository extends MongoRepository<NovelChapterCommentReply, String> {
    List<NovelChapterCommentReply> findAllByCommentId(String commentId);
}
