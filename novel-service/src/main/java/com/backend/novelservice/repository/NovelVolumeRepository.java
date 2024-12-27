package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelVolume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NovelVolumeRepository extends MongoRepository<NovelVolume, String> {
    @Query(value = "{ 'novelId': ?0 }", fields = "{ 'chapterCount': 1 }")
    List<NovelVolume> findChapterCountsByNovelId(String novelId);

    Optional<NovelVolume> findTopByNovelIdOrderByVolumeNumberDesc(String novelId);

    List<NovelVolume> findAllByNovelId(String novelId);

    @Query("{ 'novelId': ?0, 'volumeNumber': { $lt: ?1 } }")
    Optional<NovelVolume> findPreviousVolume(String novelId, int currentVolumeNumber);

    @Query("{ 'novelId': ?0, 'volumeNumber': { $gt: ?1 } }")
    Optional<NovelVolume> findNextVolume(String novelId, int currentVolumeNumber);

}
