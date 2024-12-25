package com.backend.commentservice.mapper;

import com.backend.commentservice.dto.request.NovelCommentRequest;
import com.backend.commentservice.dto.response.NovelCommentResponse;
import com.backend.commentservice.entity.NovelComment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelCommentMapper {
    NovelComment toNovelComment(NovelCommentRequest request);

    NovelCommentResponse toNovelCommentResponse(NovelComment comment);

    void updateNovelCommentFromRequest(@MappingTarget NovelComment comment, NovelCommentRequest request);
}
