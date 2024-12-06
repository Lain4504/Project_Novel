package com.backend.novelservice.service;

import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.request.NovelUpdateRequest;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.entity.NovelCategory;
import com.backend.novelservice.mapper.NovelCategoryMapper;
import com.backend.novelservice.mapper.NovelMapper;
import com.backend.novelservice.repository.NovelCategoryRepository;
import com.backend.novelservice.repository.NovelRepository;
import com.backend.utils.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelService {
    NovelRepository novelRepository;
    NovelCategoryRepository novelCategoryRepository;
    NovelMapper novelMapper;
    NovelCategoryMapper novelCategoryMapper;
    DateTimeFormatter dateTimeFormatter;
    public NovelResponse createNovel(NovelCreationRequest request) {
        if (novelRepository.existsByTitle(request.getTitle())) {
            throw new IllegalArgumentException("Novel with title " + request.getTitle() + " already exists");
        }
        var novel = novelMapper.toNovel(request);
        HashSet<NovelCategory> categories = new HashSet<>();
        novelCategoryRepository.findAllById(request.getCategories()).forEach(categories::add);
        novel.setCategories(categories);
        novel = novelRepository.save(novel);
        return novelMapper.toNovelResponse(novel);
    }
    @PostAuthorize("returnObject.email == authentication.name")
    public NovelResponse updateNovel(String novelId, NovelUpdateRequest request) {
        var novel = novelRepository.findById(novelId).orElseThrow(() -> new IllegalArgumentException("Novel with id " + novelId + " not found"));
        if (novelRepository.existsByTitle(request.getTitle()) && !novel.getTitle().equals(request.getTitle())) {
            throw new IllegalArgumentException("Novel with title " + request.getTitle() + " already exists");
        }
        novelMapper.updateNovel(novel, request);
        var categories = new HashSet<NovelCategory>();
        novelCategoryRepository.findAllById(request.getCategories()).forEach(categories::add);
        novel.setCategories(categories);
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
            novelResponse.setCreated(dateTimeFormatter.format(Instant.from(novel.getCreatedDate())));
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
