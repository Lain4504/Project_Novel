package com.backend.commentservice.repository;

import com.backend.commentservice.entity.PostCommentReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentReplyRepository extends MongoRepository<PostCommentReply, String> {
    Page<PostCommentReply> findAllByCommentId(String commentId, Pageable pageable);
}
