package com.backend.commentservice.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.commentservice.entity.PostComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends MongoRepository<PostComment, String> {

    List<PostComment> findAllByPostId(String postId);
}
