package com.backend.commentservice.repository;

import com.backend.commentservice.entity.NovelChapterComment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NovelChapterCommentRepository extends MongoRepository<NovelChapterComment, String> {
}
