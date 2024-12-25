package com.backend.commentservice.mapper;


import com.backend.commentservice.dto.request.PostCommentRequest;
import com.backend.commentservice.dto.response.PostCommentResponse;
import com.backend.commentservice.entity.PostComment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostCommentMapper {
    PostComment toPostComment(PostCommentRequest request);

    PostCommentResponse toPostCommentResponse(PostComment postComment);

    void updatePostComment(@MappingTarget PostComment postComment, PostCommentRequest request);
}
