package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.NovelChapterRequest;
import com.backend.novelservice.dto.response.NovelChapterResponse;
import com.backend.novelservice.entity.NovelChapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelChapterMapper {
    NovelChapter toNovelChapter(NovelChapterRequest request);
    NovelChapterResponse toNovelChapterResponse(NovelChapter novelChapter);
    void updateNovelChapter(@MappingTarget  NovelChapter novelChapter, NovelChapterRequest request);
}

