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
    @Mapping(target = "volumes", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Novel toNovel(NovelCreationRequest request);

    // Map Novel to NovelResponse, including image
    @Mapping(target = "volumes", source = "volumes")
    @Mapping(target = "volumes.chapters", ignore = true) // Important
    NovelResponse toNovelResponse(Novel novel);

    // Update Novel with NovelUpdateRequest, ignoring volumes and categories
    @Mapping(target = "volumes", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateNovel(@MappingTarget Novel novel, NovelUpdateRequest request);
}