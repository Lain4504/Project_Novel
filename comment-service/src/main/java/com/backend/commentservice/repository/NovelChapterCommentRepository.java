package com.backend.commentservice.repository;

import com.backend.commentservice.entity.NovelChapterComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NovelChapterCommentRepository extends MongoRepository<NovelChapterComment, String> {
    Page<NovelChapterComment> findAllByChapterId(String chapterId, Pageable pageable);
}
