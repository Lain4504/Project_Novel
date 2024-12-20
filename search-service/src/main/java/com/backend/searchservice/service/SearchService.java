package com.backend.searchservice.service;

import com.backend.event.NovelDataSenderEvent;
import com.backend.searchservice.dto.response.SearchResponse;
import com.backend.searchservice.entity.Novel;
import com.backend.searchservice.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SearchService {
    SearchRepository searchRepository;
    public void handleCreate(NovelDataSenderEvent data) {
        Map<String, Object> novelData = (Map<String, Object>) data.getParam().get("data");
        log.info("Novel data: " + novelData);
        Novel novel = mapToNovel(novelData);
        searchRepository.save(novel);
        log.info("Handled novel creation: " + novel);
    }

    public void handleUpdate(NovelDataSenderEvent data) {
        Map<String, Object> novelData = (Map<String, Object>) data.getParam().get("data");
        Novel novel = mapToNovel(novelData);
        searchRepository.save(novel);
        System.out.println("Handled novel update: " + novel);
    }

    public void handleDelete(NovelDataSenderEvent data) {
        Map<String, Object> novelData = (Map<String, Object>) data.getParam().get("data");
        Novel novel = mapToNovel(novelData);
        searchRepository.deleteById(novel.getId());
        System.out.println("Handled novel deletion: " + novel.getId());
    }

    private Novel mapToNovel(Map<String, Object> novelData) {
    return Novel.builder()
            .id((String) novelData.get("id"))
            .title((String) novelData.get("title"))
            .categories(novelData.get("categories") != null ? new HashSet<>((List<String>) novelData.get("categories")) : new HashSet<>())
            .authorId((String) novelData.get("authorId"))
            .authorName((String) novelData.get("authorName"))
            .description((String) novelData.get("description"))
            .score((Integer) novelData.get("score"))
            .bookStatus((Integer) novelData.get("bookStatus"))
            .visitCount(novelData.get("visitCount") != null ? ((Number) novelData.get("visitCount")).longValue() : null)
            .wordCount(novelData.get("wordCount") != null ? ((Number) novelData.get("wordCount")).longValue() : null)
            .commentCount(novelData.get("commentCount") != null ? ((Number) novelData.get("commentCount")).longValue() : null)
            .isVip((Integer) novelData.get("isVip"))
            .createdDate(novelData.get("createdDate") != null ? Instant.ofEpochMilli(((Number) novelData.get("createdDate")).longValue()) : null)
            .updateDateTime(novelData.get("updateDateTime") != null ? Instant.ofEpochMilli(((Number) novelData.get("updateDateTime")).longValue()) : null)
            .volumeIds((List<String>) novelData.get("volumeIds"))
            .imageUrl((String) novelData.get("imageUrl"))
            .chapterCount((Integer) novelData.get("chapterCount"))
            .latestChapterTitle((String) novelData.get("latestChapterTitle"))
            .latestChapterId((String) novelData.get("latestChapterId"))
            .latestChapterTime(novelData.get("latestChapterTime") != null ? Instant.ofEpochMilli(((Number) novelData.get("latestChapterTime")).longValue()) : null)
            .status((String) novelData.get("status"))
            .build();
}

    public List<SearchResponse> searchNovels(String query) {
        return null;
    }
}