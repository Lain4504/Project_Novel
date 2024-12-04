package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelVolume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelVolumeRepository extends MongoRepository<NovelVolume, String> {
}
