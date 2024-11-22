package com.backend.profileservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.profileservice.service.UserProfileService;
import com.backend.profileservice.dto.request.UserProfileUpdateRequest;
import com.backend.profileservice.dto.response.UserProfileResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/users/{profileId}")
    UserProfileResponse getUserProfile(@PathVariable String profileId) {
        return userProfileService.getProfile(profileId);
    }

    @DeleteMapping("/users/{profileId}")
    void deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteProfile(profileId);
    }

    @PutMapping("/users/{profileId}")
    UserProfileResponse updateUserProfile(
            @PathVariable String profileId, @RequestBody UserProfileUpdateRequest request) {
        return userProfileService.updateProfile(profileId, request);
    }

    @GetMapping("/users")
    ResponseEntity<List<UserProfileResponse>> getAllProfiles() {
        return ResponseEntity.ok(userProfileService.getAllProfiles());
    }
}
