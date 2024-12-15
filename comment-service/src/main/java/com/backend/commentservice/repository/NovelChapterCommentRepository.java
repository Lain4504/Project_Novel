package com.backend.commentservice.repository;

import com.backend.commentservice.entity.NovelChapterComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NovelChapterCommentRepository extends MongoRepository<NovelChapterComment, String> {
    List<NovelChapterComment> findAllByChapterId(String chapterId);
}
