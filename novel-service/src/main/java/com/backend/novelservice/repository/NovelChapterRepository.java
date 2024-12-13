package com.backend.novelservice.repository;

import com.backend.novelservice.entity.NovelChapter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelChapterRepository extends MongoRepository<NovelChapter, String> {
    Optional<NovelChapter> findTopByVolumeIdAndChapterNumberLessThanOrderByChapterNumberDesc(String volumeId, Integer chapterNumber);
    Optional<NovelChapter> findTopByVolumeIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String volumeId, Integer chapterNumber);
    Optional<NovelChapter> findByVolumeIdAndChapterNumber(String volumeId, Integer chapterNumber);
}
