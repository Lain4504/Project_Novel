package com.backend.novelservice.service;

import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.entity.NovelCategory;
import com.backend.novelservice.mapper.NovelCategoryMapper;
import com.backend.novelservice.mapper.NovelMapper;
import com.backend.novelservice.repository.NovelCategoryRepository;
import com.backend.novelservice.repository.NovelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelService {
    NovelRepository novelRepository;
    NovelCategoryRepository novelCategoryRepository;
    NovelMapper novelMapper;
    NovelCategoryMapper novelCategoryMapper;
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
    public NovelResponse updateNovel(String novelId, NovelCreationRequest request) {
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
    public List<NovelResponse> getNovels() {
        return novelRepository.findAll().stream().map(novelMapper::toNovelResponse).toList();
    }
}
