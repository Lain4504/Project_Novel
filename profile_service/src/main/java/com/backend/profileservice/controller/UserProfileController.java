package com.backend.profileservice.controller;

import com.backend.profileservice.Service.UserProfileService;
import com.backend.profileservice.dto.request.ProfileCreationRequest;
import com.backend.profileservice.dto.request.UserProfileUpdateRequest;
import com.backend.profileservice.dto.response.UserProfileResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users")
     UserProfileResponse createUserProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }
    @GetMapping("/users/{profileId}")
    UserProfileResponse getUserProfile(@PathVariable String profileId) {
        return userProfileService.getProfile(profileId);
    }
    @DeleteMapping("/users/{profileId}")
    void deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteProfile(profileId);
    }
    @PutMapping("/users/{profileId}")
    UserProfileResponse updateUserProfile(@PathVariable String profileId, @RequestBody UserProfileUpdateRequest request) {
        return userProfileService.updateProfile(profileId, request);
    }
}
