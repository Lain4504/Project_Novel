package com.backend.repository;
import com.backend.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, String> {

    List<Image> findByOrderByCreatedAtDesc();
}