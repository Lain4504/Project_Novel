package com.backend.commentservice.repository;

import com.backend.commentservice.entity.NovelCommentReply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelCommentReplyRepository extends MongoRepository<NovelCommentReply, String> {
}
