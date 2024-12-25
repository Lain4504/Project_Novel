package com.backend.commentservice.mapper;

import com.backend.commentservice.dto.request.NovelChapterCommentReplyRequest;
import com.backend.commentservice.dto.response.NovelChapterCommentReplyResponse;
import com.backend.commentservice.entity.NovelChapterCommentReply;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NovelChapterCommentReplyMapper {
    NovelChapterCommentReply toNovelChapterCommentReply(NovelChapterCommentReplyRequest request);

    NovelChapterCommentReplyResponse toNovelChapterCommentReplyResponse(NovelChapterCommentReply novelChapterCommentReply);

    void updateNovelChapterCommentReply(@MappingTarget NovelChapterCommentReply novelChapterCommentReply, NovelChapterCommentReplyRequest request);

}
