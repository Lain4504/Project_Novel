package com.backend.novelservice.service;

import com.backend.novelservice.dto.request.NovelCategoryRequest;
import com.backend.novelservice.dto.response.NovelCategoryResponse;
import com.backend.novelservice.mapper.NovelCategoryMapper;
import com.backend.novelservice.repository.NovelCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovelCategoryService {
    NovelCategoryMapper novelCategoryMapper;
    NovelCategoryRepository novelCategoryRepository;
    public NovelCategoryResponse createNovelCategory(NovelCategoryRequest request) {
        var category = novelCategoryMapper.toNovel(request);
        var savedCategory = novelCategoryRepository.save(category);
        return novelCategoryMapper.toNovelCategoryResponse(savedCategory);
    }
    public NovelCategoryResponse getNovelCategoryById(String id) {
        var category = novelCategoryRepository.findById(id).orElse(null);
        return novelCategoryMapper.toNovelCategoryResponse(category);
    }
    public List<NovelCategoryResponse> getNovelCategories() {
        return novelCategoryRepository.findAll().stream().map(novelCategoryMapper::toNovelCategoryResponse).toList();
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
