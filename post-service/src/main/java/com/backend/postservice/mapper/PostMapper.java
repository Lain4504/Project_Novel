package com.backend.postservice.mapper;

import com.backend.postservice.dto.response.PostResponse;
import com.backend.postservice.entity.Post;
import com.backend.postservice.entity.PostCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mappings({
            @Mapping(source = "post.id", target = "id"),
            @Mapping(source = "post.title", target = "title"),
            @Mapping(source = "post.userId", target = "userId"),
            @Mapping(source = "post.content", target = "content"),
            @Mapping(source = "category.name", target = "categoryName"),
            @Mapping(source = "post.createdDate", target = "createdDate"),
            @Mapping(source = "post.modifiedDate", target = "modifiedDate"),
            @Mapping(target = "created", ignore = true)
    })
    PostResponse toPostResponse(Post post, PostCategory category);
}
