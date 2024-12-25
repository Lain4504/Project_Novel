package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelVolume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelVolumeRepository extends MongoRepository<NovelVolume, String> {
    @Query(value = "{ 'novelId': ?0 }", fields = "{ 'chapterCount': 1 }")
    List<NovelVolume> findChapterCountsByNovelId(String novelId);

}
