package com.backend.novelservice.service;

import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelChapterRequest;
import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelChapterResponse;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.entity.Novel;
import com.backend.novelservice.entity.NovelChapter;
import com.backend.novelservice.entity.NovelVolume;
import com.backend.novelservice.mapper.NovelChapterMapper;
import com.backend.novelservice.repository.NovelChapterRepository;
import com.backend.novelservice.repository.NovelVolumeRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterService {
    NovelChapterRepository novelChapterRepository;
    NovelChapterMapper novelChapterMapper;
    DateTimeFormatter dateTimeFormatter;
    NovelVolumeRepository novelVolumeRepository;
    public NovelChapterResponse createChapter(String volumeId, NovelChapterRequest request) {
        // Retrieve the Novel by ID
        NovelVolume volume = novelVolumeRepository.findById(volumeId).orElseThrow(() -> new RuntimeException("Novel not found"));
        // Map the request to a NovelVolume entity
        NovelChapter novelChapter = novelChapterMapper.toNovelChapter(request);
        novelChapter.setCreatedDate(Instant.now());
        novelChapter.setVolume(volume);
        // Save the new volume
        NovelChapter savedChapter = novelChapterRepository.save(novelChapter);
        // Ensure the volumes list is initialized
        List<NovelChapter> chapters = volume.getChapters();
        if (chapters == null) {
            chapters = new ArrayList<>(); // Initialize if null
        }
        chapters.add(savedChapter); // Add the new volume to the list
        volume.setChapters(chapters);
        // Save the updated novel with the added volume
        novelVolumeRepository.save(volume);
        // Map and return the response
        return novelChapterMapper.toNovelChapterResponse(novelChapter);
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
    var chapter = novelChapterRepository.findById(chapterId)
            .orElseThrow(() -> new RuntimeException("Chapter not found"));
    var chapterResponse = novelChapterMapper.toNovelChapterResponse(chapter);
    chapterResponse.setCreated(dateTimeFormatter.format(Instant.from(chapter.getCreatedDate())));
    return chapterResponse;
}
    public PageResponse<NovelChapterResponse> getChapters(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelChapterRepository.findAll(pageable);
        var chapterList = pageData.getContent().stream().map(novelChapter -> {
            var chapterResponse = novelChapterMapper.toNovelChapterResponse(novelChapter);
            chapterResponse.setCreated(dateTimeFormatter.format(Instant.from(novelChapter.getCreatedDate())));
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

    public List<NovelChapterResponse> getChaptersByVolumeId(String volumeId) {
        // Retrieve the NovelVolume by ID
        var volume = novelVolumeRepository.findById(volumeId)
                .orElseThrow(() -> new RuntimeException("Volume not found"));

        // Get chapters and handle null case
        List<NovelChapter> chapters = volume.getChapters();
        if (chapters == null) {
            return List.of(); // Return an empty list if no chapters
        }

        // Map chapters to responses
        return chapters.stream()
                .map(novelChapterMapper::toNovelChapterResponse)
                .toList();
    }
}
