package com.backend.profileservice.service;

import com.backend.profileservice.dto.request.UserNovelRatingRequest;
import com.backend.profileservice.dto.response.UserNovelRatingResponse;
import com.backend.profileservice.entity.UserNovelRating;
import com.backend.profileservice.mapper.UserNovelRatingMapper;
import com.backend.profileservice.repository.UserNovelRatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserNovelRatingService {
    UserNovelRatingRepository userNovelRatingRepository;
    UserNovelRatingMapper userNovelRatingMapper;
    public UserNovelRatingResponse rateNovel(UserNovelRatingRequest request) {
        UserNovelRating userNovelRating = userNovelRatingMapper.toUserNovelRating(request);
        userNovelRatingRepository.save(userNovelRating);
        return userNovelRatingMapper.toUserNovelRatingResponse(userNovelRating);
    }
    public UserNovelRatingResponse updateRating(UserNovelRatingRequest request) {
        UserNovelRating userNovelRating = userNovelRatingRepository.findByUserIdAndNovelId(request.getUserId(), request.getNovelId());
        userNovelRatingMapper.updateUserNovelRating(userNovelRating, request);
        userNovelRatingRepository.save(userNovelRating);
        return userNovelRatingMapper.toUserNovelRatingResponse(userNovelRating);
    }
    public void deleteRating(UserNovelRatingRequest request) {
        userNovelRatingRepository.deleteByUserIdAndNovelId(request.getUserId(), request.getNovelId());
    }
}
