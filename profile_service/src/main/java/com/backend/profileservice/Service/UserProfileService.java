package com.backend.profileservice.Service;

import com.backend.profileservice.dto.request.ProfileCreationRequest;
import com.backend.profileservice.dto.request.UserProfileUpdateRequest;
import com.backend.profileservice.dto.response.UserProfileResponse;
import com.backend.profileservice.entity.UserProfile;
import com.backend.profileservice.mapper.UserProfileMapper;
import com.backend.profileservice.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
    public void deleteProfile(String id) {
        userProfileRepository.deleteById(id);
    }
    public UserProfileResponse updateProfile(String id, UserProfileUpdateRequest request) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        userProfileMapper.updateUserProfile(userProfile, request);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
