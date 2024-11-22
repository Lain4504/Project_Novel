package com.backend.postservice.service;

import com.backend.postservice.dto.request.PostCategoryRequest;
import com.backend.postservice.dto.response.PostCategoryResponse;
import com.backend.postservice.entity.PostCategory;
import com.backend.postservice.mapper.PostCategoryMapper;
import com.backend.postservice.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PostCategoryService {
    PostCategoryRepository postCategoryRepository;
    PostCategoryMapper postCategoryMapper;

    public PostCategoryResponse createPostCategory(PostCategoryRequest postCategory){
        var newPostCategory = PostCategory.builder()
                .name(postCategory.getName())
                .build();
        newPostCategory = postCategoryRepository.save(newPostCategory);
        return postCategoryMapper.toPostCategoryResponse(newPostCategory);
    }

    public List<PostCategoryResponse> getAllPostCategories(){
        return postCategoryRepository.findAll().stream()
                .map(postCategoryMapper::toPostCategoryResponse)
                .toList();
    }

    public PostCategoryResponse getPostCategory(String postCategoryId){
        var postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new RuntimeException("Post category not found"));
        return postCategoryMapper.toPostCategoryResponse(postCategory);
    }

    public void deletePostCategory(String postCategoryId){
        var postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new RuntimeException("Post category not found"));
        postCategoryRepository.delete(postCategory);
    }

    public PostCategoryResponse updatePostCategory(String postCategoryId, PostCategoryRequest postCategoryRequest){
        var postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new RuntimeException("Post category not found"));
        postCategory.setName(postCategoryRequest.getName());
        postCategory = postCategoryRepository.save(postCategory);
        return postCategoryMapper.toPostCategoryResponse(postCategory);
    }
}
