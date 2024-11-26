package com.backend.novelservice.service;

import com.backend.novelservice.dto.request.NovelCategoryRequest;
import com.backend.novelservice.dto.response.NovelCategoryResponse;
import com.backend.novelservice.dto.response.PageResponse;
import com.backend.novelservice.mapper.NovelCategoryMapper;
import com.backend.novelservice.repository.NovelCategoryRepository;
import com.backend.novelservice.utils.DateTimeFormatter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class NovelCategoryService {
    NovelCategoryMapper novelCategoryMapper;
    NovelCategoryRepository novelCategoryRepository;
    DateTimeFormatter dateTimeFormatter;
    public NovelCategoryResponse createNovelCategory(NovelCategoryRequest request) {
        var category = novelCategoryMapper.toNovel(request);
        var savedCategory = novelCategoryRepository.save(category);
        return novelCategoryMapper.toNovelCategoryResponse(savedCategory);
    }
    public NovelCategoryResponse getNovelCategoryById(String id) {
        var category = novelCategoryRepository.findById(id).orElse(null);
        return novelCategoryMapper.toNovelCategoryResponse(category);
    }
    public PageResponse<NovelCategoryResponse> getNovelCategories(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelCategoryRepository.findAll(pageable);
        var categoryList = pageData.getContent().stream().map(novelCategory -> {
            var categoryResponse = novelCategoryMapper.toNovelCategoryResponse(novelCategory);
            categoryResponse.setCreatedDate(LocalDateTime.parse(dateTimeFormatter.format(Instant.from(novelCategory.getCreatedDate()))));
            return categoryResponse;
        }).toList();
        return PageResponse.<NovelCategoryResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(categoryList)
                .build();
    }
    public void deleteNovelCategory(String id) {
        novelCategoryRepository.deleteById(id);
    }
    public NovelCategoryResponse updateNovelCategory(String id, NovelCategoryRequest request) {
        var category = novelCategoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        novelCategoryMapper.updateNovelCategory(category, request);
        var savedCategory = novelCategoryRepository.save(category);
        return novelCategoryMapper.toNovelCategoryResponse(savedCategory);
    }
}
