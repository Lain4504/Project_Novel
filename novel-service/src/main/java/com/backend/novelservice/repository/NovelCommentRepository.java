package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelCommentRepository extends MongoRepository<NovelComment, String> {
}
