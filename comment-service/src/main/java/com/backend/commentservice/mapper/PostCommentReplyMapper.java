package com.backend.commentservice.mapper;

import com.backend.commentservice.dto.request.PostCommentReplyRequest;
import com.backend.commentservice.dto.response.PostCommentReplyResponse;
import com.backend.commentservice.entity.PostCommentReply;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostCommentReplyMapper {
    PostCommentReply toPostCommentReply(PostCommentReplyRequest request);
    PostCommentReplyResponse toPostCommentReplyResponse(PostCommentReply postCommentReply);
    void updatePostCommentReplyFromRequest(@MappingTarget PostCommentReply postCommentReply, PostCommentReplyRequest request);

}
