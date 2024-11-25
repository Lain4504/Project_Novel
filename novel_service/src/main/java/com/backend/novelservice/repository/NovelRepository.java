package com.backend.novelservice.repository;

import com.backend.novelservice.entity.Novel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelRepository extends MongoRepository<Novel, String> {
    boolean existsByTitle(String title);
    Optional<Novel> findByTitle(String title);
}
