package com.backend.profileservice.service;

import com.backend.event.NovelDataSenderEvent;
import com.backend.profileservice.dto.request.UserNovelRatingRequest;
import com.backend.profileservice.dto.response.UserNovelRatingResponse;
import com.backend.profileservice.entity.UserNovelRating;
import com.backend.profileservice.mapper.UserNovelRatingMapper;
import com.backend.profileservice.repository.UserNovelRatingRepository;
import com.backend.profileservice.repository.httpclient.NovelServiceClient;
import jakarta.validation.constraints.NotNull;
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
    NovelServiceClient client;
    public UserNovelRatingResponse rateNovel(UserNovelRatingRequest request) {
        UserNovelRating userNovelRating = userNovelRatingMapper.toUserNovelRating(request);
        client.updateNovelRating(request.getNovelId(), request.getRating());
        userNovelRating = userNovelRatingRepository.save(userNovelRating);
        return userNovelRatingMapper.toUserNovelRatingResponse(userNovelRating);
    }
    public UserNovelRatingResponse updateRating(UserNovelRatingRequest request) {
        UserNovelRating userNovelRating = userNovelRatingRepository.findByUserIdAndNovelId(request.getUserId(), request.getNovelId());
        long oldRating = userNovelRating.getRating();
        long newRating = request.getRating();
        userNovelRatingMapper.updateUserNovelRating(userNovelRating, request);
        Map<String, Long> ratings = Map.of("newRating", newRating, "oldRating", oldRating);
        client.updateNovelRatingWithSameUser(request.getNovelId(), ratings);
        userNovelRatingRepository.save(userNovelRating);
        return userNovelRatingMapper.toUserNovelRatingResponse(userNovelRating);
    }

    public boolean hasRatedNovel(String userId, String novelId) {
        return userNovelRatingRepository.findByUserIdAndNovelId(userId, novelId) != null;
    }
}
