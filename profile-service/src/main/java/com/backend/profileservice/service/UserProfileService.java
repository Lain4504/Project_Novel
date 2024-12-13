package com.backend.profileservice.service;

import java.time.Instant;
import java.util.List;

import com.backend.profileservice.entity.Image;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    ImageService imageService;
    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile.setCreatedAt(Instant.now());
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getProfile(String userId) {
        UserProfile userProfile =
                userProfileRepository.findByUserId((userId)).orElseThrow(() -> new RuntimeException("Profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public void deleteProfile(String id) {
        userProfileRepository.deleteById(id);
    }

    public UserProfileResponse updateProfile(String id, UserProfileUpdateRequest request, MultipartFile imageFile) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        userProfileMapper.updateUserProfile(userProfile, request);
        if (imageFile != null && !imageFile.isEmpty()) {
            if (userProfile.getImage() != null && !userProfile.getImage().getPath().equals(request.getImageUrl())) {
                imageService.deleteImage(userProfile.getImage().getId());
                Image newImage = imageService.uploadImage(imageFile);
                userProfile.setImage(newImage);
            }
        } else if (userProfile.getImage() == null || !userProfile.getImage().getPath().equals(request.getImageUrl())) {
            Image newImage = imageService.uploadImage(imageFile);
            userProfile.setImage(newImage);
        }
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getAllProfiles() {
        var userProfiles = userProfileRepository.findAll();
        return userProfiles.stream()
                .map(userProfileMapper::toUserProfileResponse)
                .toList();
    }
}
