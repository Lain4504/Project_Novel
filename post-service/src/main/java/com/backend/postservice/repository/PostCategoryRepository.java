package com.backend.postservice.repository;

import com.backend.postservice.entity.PostCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostCategoryRepository extends MongoRepository<PostCategory, String> {

}
