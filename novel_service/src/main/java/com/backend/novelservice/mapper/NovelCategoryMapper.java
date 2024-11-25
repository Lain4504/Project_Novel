package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelCategoryRequest;
import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.response.NovelCategoryResponse;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.entity.Novel;
import com.backend.novelservice.entity.NovelCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelCategoryMapper {
    NovelCategory toNovel(NovelCategoryRequest request);
    NovelCategoryResponse toNovelCategoryResponse(NovelCategory novelCategory);
    void updateNovelCategory(NovelCategory novelCategory, NovelCategoryRequest request);
}
