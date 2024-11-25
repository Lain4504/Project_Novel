package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelChapter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelChapterRepository extends MongoRepository<NovelChapter, String> {
}
