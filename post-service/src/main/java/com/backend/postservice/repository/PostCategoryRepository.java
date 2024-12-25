package com.backend.postservice.repository;

import com.backend.postservice.entity.PostCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoryRepository extends MongoRepository<PostCategory, String> {
}