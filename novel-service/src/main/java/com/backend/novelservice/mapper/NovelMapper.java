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
    // Ánh xạ NovelCreationRequest sang Novel, bỏ qua volumes và categories
    @Mapping(target = "volumes", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Novel toNovel(NovelCreationRequest request);

    // Ánh xạ Novel sang NovelResponse, bỏ qua danh sách chapters trong NovelVolume
    @Mapping(target = "volumes", source = "volumes")
    @Mapping(target = "volumes.chapters", ignore = true) // Quan trọng
    NovelResponse toNovelResponse(Novel novel);

    // Cập nhật Novel với NovelUpdateRequest, bỏ qua volumes và categories
    @Mapping(target = "volumes", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateNovel(@MappingTarget Novel novel, NovelUpdateRequest request);
}
