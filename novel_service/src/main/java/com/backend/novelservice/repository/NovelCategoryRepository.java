package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelCategoryRepository extends MongoRepository<NovelCategory, String> {
}
