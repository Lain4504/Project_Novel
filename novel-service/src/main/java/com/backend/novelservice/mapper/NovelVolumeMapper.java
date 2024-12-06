package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.entity.NovelVolume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelVolumeMapper {
    @Mapping(target = "chapters", ignore = true)
    NovelVolume toNovelVolume(NovelVolumeRequest request);
    @Mapping(target = "chapters", source = "chapters")
    NovelVolumeResponse toNovelVolumeResponse(NovelVolume novelVolume);
    @Mapping(target = "chapters", ignore = true)
    void updateNovelVolume(@MappingTarget NovelVolume novelVolume, NovelVolumeRequest request);
}
