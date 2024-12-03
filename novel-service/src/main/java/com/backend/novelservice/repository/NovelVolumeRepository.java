package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelVolume;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NovelVolumeRepository extends MongoRepository<NovelVolume, String> {
}
