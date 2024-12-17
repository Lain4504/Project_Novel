package com.backend.commentservice.repository;

import com.backend.commentservice.entity.NovelChapterCommentReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelChapterCommentReplyRepository extends MongoRepository<NovelChapterCommentReply, String> {
    Page<NovelChapterCommentReply> findAllByCommentId(String commentId, Pageable pageable);
}
