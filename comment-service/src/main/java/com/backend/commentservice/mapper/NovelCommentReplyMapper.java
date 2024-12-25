package com.backend.commentservice.mapper;

import com.backend.commentservice.dto.request.NovelCommentReplyRequest;
import com.backend.commentservice.dto.response.NovelCommentReplyResponse;
import com.backend.commentservice.entity.NovelCommentReply;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelCommentReplyMapper {
    NovelCommentReply toNovelCommentReply(NovelCommentReplyRequest request);

    NovelCommentReplyResponse toNovelCommentReplyResponse(NovelCommentReply novelCommentReply);

    void updateNovelCommentReplyFromRequest(@MappingTarget NovelCommentReply novelCommentReply, NovelCommentReplyRequest request);
}
