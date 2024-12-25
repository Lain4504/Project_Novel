package com.backend.novelservice.repository;

import com.backend.novelservice.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelRepository extends MongoRepository<Novel, String> {
    boolean existsByTitle(String title);

    Page<Novel> findByAuthorId(String authorId, Pageable pageable);

    Page<Novel> findByStatus(String status, Pageable pageable);
}
