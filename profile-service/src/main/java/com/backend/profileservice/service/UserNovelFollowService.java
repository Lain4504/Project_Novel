package com.backend.profileservice.service;

import com.backend.profileservice.dto.request.UserNovelFollowRequest;
import com.backend.profileservice.dto.response.UserNovelFollowResponse;
import com.backend.profileservice.entity.UserNovelFollow;
import com.backend.profileservice.mapper.UserNovelFollowMapper;
import com.backend.profileservice.repository.UserNovelFollowRepository;
import com.backend.profileservice.repository.httpclient.NovelDetailsResponse;
import com.backend.profileservice.repository.httpclient.NovelServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserNovelFollowService {
    UserNovelFollowRepository userNovelFollowRepository;
    UserNovelFollowMapper userNovelFollowMapper;
    NovelServiceClient novelServiceClient;
    public UserNovelFollowResponse followNovel(UserNovelFollowRequest request) {
        UserNovelFollow userNovelFollow = userNovelFollowMapper.toUserNovelFollow(request);
        userNovelFollowRepository.save(userNovelFollow);
        return userNovelFollowMapper.toUserNovelFollowResponse(userNovelFollow);
    }
    public void unfollowNovel(UserNovelFollowRequest request) {
        userNovelFollowRepository.deleteByUserIdAndNovelId(request.getUserId(), request.getNovelId());
    }
    public boolean isFollowingNovel(UserNovelFollowRequest request) {
        return userNovelFollowRepository.findByUserIdAndNovelId(request.getUserId(), request.getNovelId()) != null;
    }

    public List<String> getFollowingNovelIds(String userId) {
        List<UserNovelFollow> userNovelFollows = userNovelFollowRepository.findAllByUserId(userId);
        return userNovelFollows.stream()
                .map(UserNovelFollow::getNovelId) //  Chỉ trích xuất novelId
                .toList();
    }
    public List<NovelDetailsResponse> getFollowingNovelsWithDetails(String userId) {
        List<String> novelIds = getFollowingNovelIds(userId);
        if (novelIds.isEmpty()) {
            return Collections.emptyList();
        }
        return novelServiceClient.getNovelDetails(novelIds);
    }
}
