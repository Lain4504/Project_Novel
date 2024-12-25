package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelCategoryRequest;
import com.backend.novelservice.dto.response.NovelCategoryResponse;
import com.backend.novelservice.entity.NovelCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelCategoryMapper {
    NovelCategory toNovel(NovelCategoryRequest request);

    NovelCategoryResponse toNovelCategoryResponse(NovelCategory novelCategory);

    void updateNovelCategory(@MappingTarget NovelCategory novelCategory, NovelCategoryRequest request);
}
