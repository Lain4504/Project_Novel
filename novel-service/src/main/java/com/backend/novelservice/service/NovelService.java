package com.backend.novelservice.service;

import com.backend.enums.NovelStatusEnum;
import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.request.NovelUpdateRequest;
import com.backend.novelservice.dto.response.NovelDetailsResponse;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.entity.Image;
import com.backend.novelservice.entity.NovelCategory;
import com.backend.novelservice.mapper.NovelMapper;
import com.backend.novelservice.repository.NovelCategoryRepository;
import com.backend.novelservice.repository.NovelRepository;
import com.backend.utils.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelService {
    NovelRepository novelRepository;
    NovelCategoryRepository novelCategoryRepository;
    NovelMapper novelMapper;
    DateTimeFormatter dateTimeFormatter;
    ImageService imageService;

    public NovelResponse createNovel(NovelCreationRequest request, MultipartFile imageFile) {
        if (novelRepository.existsByTitle(request.getTitle())) {
            throw new IllegalArgumentException("Novel with title " + request.getTitle() + " already exists");
        }
        var novel = novelMapper.toNovel(request);
        HashSet<NovelCategory> categories = new HashSet<>();
        novelCategoryRepository.findAllById(request.getCategories()).forEach(categories::add);
        novel.setCategories(categories);
        novel.setCreatedDate(Instant.now());
        novel.setUpdateDateTime(Instant.now());
        if (imageFile != null && !imageFile.isEmpty()) {
            Image image = imageService.uploadImage(imageFile);
            novel.setImage(image);
        }
        novel = novelRepository.save(novel);
        return novelMapper.toNovelResponse(novel);
    }

    public NovelResponse updateNovel(String novelId, NovelUpdateRequest request, MultipartFile imageFile) {
        var novel = novelRepository.findById(novelId).orElseThrow(() -> new IllegalArgumentException("Novel with id " + novelId + " not found"));
        if (novelRepository.existsByTitle(request.getTitle()) && !novel.getTitle().equals(request.getTitle())) {
            throw new IllegalArgumentException("Novel with title " + request.getTitle() + " already exists");
        }
        novelMapper.updateNovel(novel, request);
        var categories = new HashSet<NovelCategory>();
        novelCategoryRepository.findAllById(request.getCategories()).forEach(categories::add);
        novel.setCategories(categories);
        novel.setUpdateDateTime(Instant.now());
        novel.setStatus(NovelStatusEnum.valueOf(request.getStatus()));
        if (imageFile != null && !imageFile.isEmpty()) {
            if (novel.getImage() != null && !novel.getImage().getPath().equals(request.getImageUrl())) {
                imageService.deleteImage(novel.getImage().getId());
                Image newImage = imageService.uploadImage(imageFile);
                novel.setImage(newImage);
            }
        } else if (novel.getImage() == null || !novel.getImage().getPath().equals(request.getImageUrl())) {
            Image newImage = imageService.uploadImage(imageFile);
            novel.setImage(newImage);
        }

        novel = novelRepository.save(novel);
        return novelMapper.toNovelResponse(novel);
    }

    public void deleteNovel(String novelId) {
        novelRepository.deleteById(novelId);
    }

    public NovelResponse getNovel(String novelId) {
        var novel = novelRepository.findById(novelId).orElseThrow(() -> new IllegalArgumentException("Novel with id " + novelId + " not found"));
        return novelMapper.toNovelResponse(novel);
    }

    public PageResponse<NovelResponse> getNovels(int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelRepository.findAll(pageable);
        var novelList = pageData.getContent().stream().map(novel -> {
            var novelResponse = novelMapper.toNovelResponse(novel);
            novelResponse.setCreated(dateTimeFormatter.format(novel.getCreatedDate()));
            return novelResponse;
        }).toList();
        return PageResponse.<NovelResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(novelList)
                .build();
    }

    public PageResponse<NovelResponse> getMyNovels(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelRepository.findByAuthorId(userId, pageable);
        var novelList = pageData.getContent().stream().map(novel -> {
            var novelResponse = novelMapper.toNovelResponse(novel);
            novelResponse.setCreated(dateTimeFormatter.format(novel.getCreatedDate()));
            return novelResponse;
        }).toList();
        return PageResponse.<NovelResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(novelList)
                .build();
    }

    public PageResponse<NovelResponse> getNovelsByAuthor(String authorId, int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelRepository.findByAuthorId(authorId, pageable);
        var novelList = pageData.getContent().stream().map(novel -> {
            var novelResponse = novelMapper.toNovelResponse(novel);
            novelResponse.setCreated(dateTimeFormatter.format(novel.getCreatedDate()));
            return novelResponse;
        }).toList();
        return PageResponse.<NovelResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(novelList)
                .build();
    }

    public List<NovelDetailsResponse> getNovelDetails(List<String> novelIds) {
        return novelRepository.findAllById(novelIds).stream()
                .map(novel -> new NovelDetailsResponse(
                        novel.getId(),
                        novel.getTitle(),
                        novel.getAuthorName(),
                        novel.getChapterCount() // Lấy số chapter
                ))
                .collect(Collectors.toList());
    }

    public PageResponse<NovelResponse> getLatestNovels(int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("latestChapterTime").nullsLast());
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelRepository.findAll(pageable);
        var novelList = pageData.getContent().stream()
                .filter(novel -> novel.getLatestChapterTime() != null)
                .map(novel -> {
                    var novelResponse = novelMapper.toNovelResponse(novel);
                    novelResponse.setCreated(dateTimeFormatter.format(novel.getLatestChapterTime()));
                    return novelResponse;
                }).toList();
        return PageResponse.<NovelResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(novelList)
                .build();
    }
    public PageResponse<NovelResponse> getNovelsByStatus(String status, int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelRepository.findByStatus(status, pageable);
        var novelList = pageData.getContent().stream().map(novel -> {
            var novelResponse = novelMapper.toNovelResponse(novel);
            novelResponse.setCreated(dateTimeFormatter.format(novel.getCreatedDate()));
            return novelResponse;
        }).toList();
        return PageResponse.<NovelResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(novelList)
                .build();
    }
}
