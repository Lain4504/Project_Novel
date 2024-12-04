package com.backend.novelservice.service;

import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelChapterRequest;
import com.backend.novelservice.dto.response.NovelChapterResponse;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.mapper.NovelChapterMapper;
import com.backend.novelservice.repository.NovelChapterRepository;
import com.backend.utils.DateTimeFormatter;
import jakarta.persistence.MapKey;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterService {
    NovelChapterRepository novelChapterRepository;
    NovelChapterMapper novelChapterMapper;
    DateTimeFormatter dateTimeFormatter;
    public NovelChapterResponse createChapter(NovelChapterRequest request) {
        var chapter = novelChapterMapper.toNovelChapter(request);
        novelChapterRepository.save(chapter);
        return novelChapterMapper.toNovelChapterResponse(chapter);
    }
    public NovelChapterResponse updateChapter(String chapterId, NovelChapterRequest request) {
        var chapter = novelChapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("Chapter not found"));
        novelChapterMapper.updateNovelChapter(chapter, request);
        novelChapterRepository.save(chapter);
        return novelChapterMapper.toNovelChapterResponse(chapter);
    }
    public void deleteChapter(String chapterId) {
        var chapter = novelChapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("Chapter not found"));
        novelChapterRepository.delete(chapter);
    }
    public NovelChapterResponse getChapter(String chapterId) {
        var chapter = novelChapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("Chapter not found"));
        return novelChapterMapper.toNovelChapterResponse(chapter);
    }
    public PageResponse<NovelChapterResponse> getChapters(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelChapterRepository.findAll(pageable);
        var chapterList = pageData.getContent().stream().map(novelChapter -> {
            var chapterResponse = novelChapterMapper.toNovelChapterResponse(novelChapter);
            chapterResponse.setCreatedDate(LocalDateTime.parse(dateTimeFormatter.format(Instant.from(novelChapter.getCreatedDate()))));
            return chapterResponse;
        }).toList();
        return PageResponse.<NovelChapterResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(chapterList)
                .build();
    }
}
