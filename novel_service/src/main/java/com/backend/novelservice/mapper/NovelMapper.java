package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.entity.Novel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelMapper {
    Novel toNovel(NovelCreationRequest request);
    NovelResponse toNovelResponse(Novel novel);
    @Mapping(target = "categories", ignore = true)
    void updateNovel(@MappingTarget Novel novel, NovelCreationRequest request);
}
