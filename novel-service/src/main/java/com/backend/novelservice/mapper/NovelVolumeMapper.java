package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.entity.NovelVolume;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelVolumeMapper {
    // Bỏ qua chapters khi ánh xạ NovelVolumeRequest sang NovelVolume
    NovelVolume toNovelVolume(NovelVolumeRequest request);

    // Không ánh xạ chapters khi trả về NovelVolumeResponse
    NovelVolumeResponse toNovelVolumeResponse(NovelVolume novelVolume);

    // Cập nhật NovelVolume với NovelVolumeRequest, bỏ qua chapters
    void updateNovelVolume(@MappingTarget NovelVolume novelVolume, NovelVolumeRequest request);
}
