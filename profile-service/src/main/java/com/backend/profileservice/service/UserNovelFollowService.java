package com.backend.profileservice.service;

import com.backend.profileservice.dto.request.UserNovelFollowRequest;
import com.backend.profileservice.dto.response.UserNovelFollowResponse;
import com.backend.profileservice.entity.UserNovelFollow;
import com.backend.profileservice.entity.UserProfile;
import com.backend.profileservice.mapper.UserNovelFollowMapper;
import com.backend.profileservice.repository.UserNovelFollowRepository;
import com.backend.profileservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserNovelFollowService {
    UserNovelFollowRepository userNovelFollowRepository;
    UserNovelFollowMapper userNovelFollowMapper;
    UserProfileRepository userProfileRepository;

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
}
