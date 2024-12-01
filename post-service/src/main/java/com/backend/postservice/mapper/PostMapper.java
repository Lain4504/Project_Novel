package com.backend.postservice.mapper;

import com.backend.postservice.dto.response.PostResponse;
import com.backend.postservice.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
