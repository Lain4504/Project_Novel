package com.backend.commentservice.mapper;

import com.backend.commentservice.dto.request.NovelChapterCommentRequest;
import com.backend.commentservice.dto.response.NovelChapterCommentResponse;
import com.backend.commentservice.entity.NovelChapterComment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelChapterCommentMapper {
    NovelChapterComment toNovelChapterComment(NovelChapterCommentRequest request);
    NovelChapterCommentResponse toNovelChapterCommentResponse(NovelChapterComment novelChapterComment);
    void updateNovelChapterComment(@MappingTarget NovelChapterComment novelChapterComment, NovelChapterCommentRequest request);
}
