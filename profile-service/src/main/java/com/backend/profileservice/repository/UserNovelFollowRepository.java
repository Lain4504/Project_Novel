package com.backend.profileservice.repository;

import com.backend.profileservice.entity.UserNovelFollow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNovelFollowRepository extends MongoRepository<UserNovelFollow, String> {
    UserNovelFollow findByUserIdAndNovelId(String userId, String novelId);
    void deleteByUserIdAndNovelId(String userId, String novelId);
}
