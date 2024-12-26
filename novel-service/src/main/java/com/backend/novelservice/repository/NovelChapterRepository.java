package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelChapter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NovelChapterRepository extends MongoRepository<NovelChapter, String> {
    Optional<NovelChapter> findTopByVolumeIdAndChapterNumberLessThanOrderByChapterNumberDesc(String volumeId, Integer chapterNumber);

    Optional<NovelChapter> findTopByVolumeIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String volumeId, Integer chapterNumber);

    Optional<NovelChapter> findByVolumeIdAndChapterNumber(String volumeId, Integer chapterNumber);

    @Query(value = "{ 'volumeId': ?0, 'status': ?1 }", fields = "{ 'content': 0 }")
    List<NovelChapter> findChaptersByVolumeIdWithoutContent(String volumeId, String status);

    @Query(value = "{ 'volumeId': ?0 }", fields = "{ 'content': 0 }")
    List<NovelChapter> findAllChaptersByVolumeIdWithoutContent(String volumeId);

    Optional<NovelChapter> findTopByVolumeIdOrderByChapterNumberDesc(String volumeId);

    List<NovelChapter> findAllByVolumeId(String volumeId);
}
