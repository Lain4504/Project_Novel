package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.request.NovelUpdateRequest;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.entity.Novel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelMapper {
    // Map NovelCreationRequest to Novel, ignoring volumes and categories
    @Mapping(target = "categories", ignore = true)
    Novel toNovel(NovelCreationRequest request);
    NovelResponse toNovelResponse(Novel novel);

    // Update Novel with NovelUpdateRequest, ignoring volumes and categories
    @Mapping(target = "categories", ignore = true)
    void updateNovel(@MappingTarget Novel novel, NovelUpdateRequest request);
}