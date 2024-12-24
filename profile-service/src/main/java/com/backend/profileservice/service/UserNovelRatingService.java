package com.backend.profileservice.service;

import com.backend.event.NotificationEvent;
import com.backend.event.NovelDataSenderEvent;
import com.backend.profileservice.dto.request.UserNovelRatingRequest;
import com.backend.profileservice.dto.response.UserNovelRatingResponse;
import com.backend.profileservice.entity.UserNovelRating;
import com.backend.profileservice.mapper.UserNovelRatingMapper;
import com.backend.profileservice.repository.UserNovelRatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserNovelRatingService {
    UserNovelRatingRepository userNovelRatingRepository;
    UserNovelRatingMapper userNovelRatingMapper;
    KafkaTemplate<String, Object> kafkaTemplate;
    public UserNovelRatingResponse rateNovel(UserNovelRatingRequest request) {
        UserNovelRating userNovelRating = userNovelRatingMapper.toUserNovelRating(request);
        NovelDataSenderEvent novelDataSenderEvent = NovelDataSenderEvent.builder()
                .channel("novel-rating")
                .param(Map.of("novelId", request.getNovelId(), "data", userNovelRatingMapper.toUserNovelRatingResponse(userNovelRating)))
                .build();
        userNovelRating = userNovelRatingRepository.save(userNovelRating);
        return userNovelRatingMapper.toUserNovelRatingResponse(userNovelRating);
    }
    public UserNovelRatingResponse updateRating(UserNovelRatingRequest request) {
        UserNovelRating userNovelRating = userNovelRatingRepository.findByUserIdAndNovelId(request.getUserId(), request.getNovelId());
        userNovelRatingMapper.updateUserNovelRating(userNovelRating, request);
        List<UserNovelRatingResponse> list = getNovelRating(request.getNovelId());
        NovelDataSenderEvent novelDataSenderEvent = NovelDataSenderEvent.builder()
                .channel("novel-rating")
                .param(Map.of("novelId", request.getNovelId(), "rating", list))
                .build();
        userNovelRatingRepository.save(userNovelRating);
        return userNovelRatingMapper.toUserNovelRatingResponse(userNovelRating);
    }
    public boolean hasRatedNovel(String userId, String novelId) {
        return userNovelRatingRepository.findByUserIdAndNovelId(userId, novelId) != null;
    }
}
