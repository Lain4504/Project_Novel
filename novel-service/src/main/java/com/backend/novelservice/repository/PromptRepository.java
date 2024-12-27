package com.backend.novelservice.repository;

import com.backend.novelservice.entity.Prompt;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepository extends MongoRepository<Prompt, String> {
}
