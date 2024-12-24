package com.backend.novelservice.service;

import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelChapterRequest;
import com.backend.novelservice.dto.response.NovelChapterResponse;
import com.backend.novelservice.entity.Novel;
import com.backend.novelservice.entity.NovelChapter;
import com.backend.novelservice.entity.NovelVolume;
import com.backend.novelservice.mapper.NovelChapterMapper;
import com.backend.novelservice.repository.NovelChapterRepository;
import com.backend.novelservice.repository.NovelRepository;
import com.backend.novelservice.repository.NovelVolumeRepository;
import com.backend.utils.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterService {
    NovelChapterRepository novelChapterRepository;
    NovelChapterMapper novelChapterMapper;
    NovelRepository novelRepository;
    DateTimeFormatter dateTimeFormatter;
    NovelVolumeRepository novelVolumeRepository;

    private int sumChapterCountByNovelId(String novelId) {
        List<NovelVolume> volumes = novelVolumeRepository.findChapterCountsByNovelId(novelId);
        return volumes.stream().mapToInt(NovelVolume::getChapterCount).sum();
    }

    public NovelChapterResponse createChapter(String volumeId, NovelChapterRequest request) {
        // Lấy thông tin NovelVolume theo volumeId
        NovelVolume volume = novelVolumeRepository.findById(volumeId)
                .orElseThrow(() -> new RuntimeException("Volume not found"));
        NovelChapter novelChapter = novelChapterMapper.toNovelChapter(request);
        novelChapter.setVolumeId(volumeId);
        novelChapter.setCreatedDate(Instant.now());
        NovelChapter savedChapter = novelChapterRepository.save(novelChapter);
        List<String> chapterIds = volume.getChapterIds();
        if (chapterIds == null) {
            chapterIds = new ArrayList<>();
        }
        chapterIds.add(savedChapter.getId());
        volume.setChapterIds(chapterIds);
        volume.setChapterCount(chapterIds.size());
        novelVolumeRepository.save(volume);
        Novel novel = novelRepository.findById(volume.getNovelId())
                .orElseThrow(() -> new RuntimeException("Novel not found"));
        novel.setLatestChapterId(savedChapter.getId());
        novel.setLatestChapterTitle(savedChapter.getChapterTitle());
        novel.setLatestChapterTime(Instant.now());
        novel.setChapterCount(sumChapterCountByNovelId(novel.getId())); // Cập nhật chapterCount
        novel.setWordCount(novel.getWordCount() + savedChapter.getWordCount());
        novelRepository.save(novel);
        return novelChapterMapper.toNovelChapterResponse(savedChapter);
    }

    public NovelChapterResponse updateChapter(String chapterId, NovelChapterRequest request) {
        var chapter = novelChapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("Chapter not found"));
        long oldWordCount = chapter.getWordCount();
        novelChapterMapper.updateNovelChapter(chapter, request);
        novelChapterRepository.save(chapter);
        long newWordCount = chapter.getWordCount();
        long wordCountDiff = newWordCount - oldWordCount;
        NovelVolume volume = novelVolumeRepository.findById(chapter.getVolumeId()).orElseThrow(() -> new RuntimeException("Volume not found"));
        Novel novel = novelRepository.findById(volume.getNovelId()).orElseThrow(() -> new RuntimeException("Novel not found"));
        novel.setWordCount(novel.getWordCount() + wordCountDiff);
        novelRepository.save(novel);
        return novelChapterMapper.toNovelChapterResponse(chapter);
    }

    public void deleteChapter(String chapterId) {
        var chapter = novelChapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("Chapter not found"));
        var volume = novelVolumeRepository.findById(chapter.getVolumeId()).orElseThrow(() -> new RuntimeException("Volume not found"));
        // Xóa chapter khỏi danh sách chapterIds của volume
        List<String> chapterIds = volume.getChapterIds();
        chapterIds.remove(chapterId);
        volume.setChapterIds(chapterIds);
        volume.setChapterCount(chapterIds.size());
        novelVolumeRepository.save(volume);
        // Cập nhật chapterCount trong Novel
        var novel = novelRepository.findById(volume.getNovelId()).orElseThrow(() -> new RuntimeException("Novel not found"));
        novel.setChapterCount(sumChapterCountByNovelId(novel.getId()));
        novel.setWordCount(novel.getWordCount() - chapter.getWordCount());
        novelRepository.save(novel);
        novelChapterRepository.delete(chapter);
    }

    public NovelChapterResponse getChapter(String chapterId) {
        var chapter = novelChapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        var chapterResponse = novelChapterMapper.toNovelChapterResponse(chapter);
        chapterResponse.setCreated(dateTimeFormatter.format(chapter.getCreatedDate()));
        var volume = novelVolumeRepository.findById(chapter.getVolumeId())
                .orElseThrow(() -> new RuntimeException("Volume not found"));
        var novel = novelRepository.findById(volume.getNovelId())
                .orElseThrow(() -> new RuntimeException("Novel not found"));
        chapterResponse.setAuthorId(novel.getAuthorId());
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

    public List<NovelChapterResponse> getChaptersByVolumeId(String volumeId, String status) {
        var volume = novelVolumeRepository.findById(volumeId)
                .orElseThrow(() -> new RuntimeException("Volume not found"));
        List<String> chapterIds = volume.getChapterIds();
        if (chapterIds == null || chapterIds.isEmpty()) {
            return List.of();
        }
        List<NovelChapter> chapters = novelChapterRepository.findAllById(chapterIds);
        if (status != null && !status.isEmpty()) {
            chapters = chapters.stream()
                    .filter(chapter -> chapter.getStatus().toString().equals(status))
                    .sorted(Comparator.comparing(NovelChapter::getChapterNumber))
                    .toList();
        } else {
            chapters = chapters.stream()
                    .sorted(Comparator.comparing(NovelChapter::getChapterNumber))
                    .toList();
        }
        return chapters.stream()
                .map(novelChapterMapper::toNovelChapterResponse)
                .toList();
    }
    public void incrementVisitCount(String chapterId){
        NovelChapter chapter = novelChapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("Chapter not found"));
        NovelVolume volume = novelVolumeRepository.findById(chapter.getVolumeId()).orElseThrow(() -> new RuntimeException("Volume not found"));
        Novel novel = novelRepository.findById(volume.getNovelId()).orElseThrow(() -> new RuntimeException("Novel not found"));
        novel.setVisitCount(novel.getVisitCount() + 1);
        novelRepository.save(novel);
    }
    // Lấy chapter theo số thứ tự( hiện tại đang dùng getChapter)
    public Optional<NovelChapter> getChapterByNumber(String volumeId, Integer chapterNumber) {
        return novelChapterRepository.findByVolumeIdAndChapterNumber(volumeId, chapterNumber);
    }

    // Lấy chapter trước đó
    public Optional<NovelChapter> getPreviousChapter(String volumeId, Integer currentChapterNumber) {
        return novelChapterRepository.findTopByVolumeIdAndChapterNumberLessThanOrderByChapterNumberDesc(volumeId, currentChapterNumber);
    }

    // Lấy chapter sau đó
    public Optional<NovelChapter> getNextChapter(String volumeId, Integer currentChapterNumber) {
        return novelChapterRepository.findTopByVolumeIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(volumeId, currentChapterNumber);
    }

}