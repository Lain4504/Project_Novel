package com.backend.postservice.mapper;

import com.backend.postservice.dto.request.PostCategoryRequest;
import com.backend.postservice.dto.response.PostCategoryResponse;
import com.backend.postservice.entity.PostCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostCategoryMapper {
    PostCategoryResponse toPostCategoryResponse(PostCategory postCategory);

    PostCategory toPostCategory(PostCategoryRequest postCategory);

    void updatePostCategory(@MappingTarget PostCategory postCategory, PostCategoryRequest postCategoryRequest);
}
