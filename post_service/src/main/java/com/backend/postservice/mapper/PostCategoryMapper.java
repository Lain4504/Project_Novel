package com.backend.postservice.mapper;

import com.backend.postservice.dto.response.PostCategoryResponse;
import com.backend.postservice.entity.PostCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostCategoryMapper {
    PostCategoryResponse toPostCategoryResponse(PostCategory postCategory);

}
