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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NovelChapterService {
    NovelChapterRepository novelChapterRepository;
    NovelChapterMapper novelChapterMapper;
    DateTimeFormatter dateTimeFormatter;
    NovelVolumeRepository novelVolumeRepository;
    public NovelChapterResponse createChapter(String volumeId, NovelChapterRequest request) {
        // Lấy thông tin NovelVolume theo volumeId
        NovelVolume volume = novelVolumeRepository.findById(volumeId)
                .orElseThrow(() -> new RuntimeException("Volume not found"));
        // Map từ request sang entity NovelChapter
        NovelChapter novelChapter = novelChapterMapper.toNovelChapter(request);
        novelChapter.setVolumeId(volumeId);
        novelChapter.setCreatedDate(Instant.now());
        // Lưu chapter mới vào database
        NovelChapter savedChapter = novelChapterRepository.save(novelChapter);
        // Đảm bảo danh sách chapterIds đã được khởi tạo
        List<String> chapterIds = volume.getChapterIds();
        if (chapterIds == null) {
            chapterIds = new ArrayList<>();
        }
        // Thêm chapterId của chapter mới vào danh sách
        chapterIds.add(savedChapter.getId());
        volume.setChapterIds(chapterIds);
        // Cập nhật số lượng chapter
        volume.setChapterCount(chapterIds.size());
        // Lưu lại thông tin volume đã cập nhật
        novelVolumeRepository.save(volume);
        // Map và trả về response
        return novelChapterMapper.toNovelChapterResponse(savedChapter);
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
    chapterResponse.setCreated(dateTimeFormatter.format(chapter.getCreatedDate()));
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
        // Lấy thông tin NovelVolume theo volumeId
        var volume = novelVolumeRepository.findById(volumeId)
                .orElseThrow(() -> new RuntimeException("Volume not found"));
        // Lấy danh sách chapterIds từ volume
        List<String> chapterIds = volume.getChapterIds();
        if (chapterIds == null || chapterIds.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng nếu không có chapter
        }
        // Lấy danh sách NovelChapter từ repository dựa trên chapterIds
        List<NovelChapter> chapters = novelChapterRepository.findAllById(chapterIds);
        // Map các NovelChapter sang NovelChapterResponse
        return chapters.stream()
                .map(novelChapterMapper::toNovelChapterResponse)
                .toList();
    }
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
