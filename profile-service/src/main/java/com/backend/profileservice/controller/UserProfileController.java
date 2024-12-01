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
    ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String profileId) {
        return ResponseEntity.ok(userProfileService.getProfile(profileId));
    }

    @DeleteMapping("/users/{profileId}")
    ResponseEntity<Void> deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteProfile(profileId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{profileId}")
    ResponseEntity<UserProfileResponse> updateUserProfile(
            @PathVariable String profileId, @RequestBody UserProfileUpdateRequest request) {
      return ResponseEntity.ok(userProfileService.updateProfile(profileId, request));
    }

    @GetMapping("/users")
    ResponseEntity<List<UserProfileResponse>> getAllProfiles() {
        return ResponseEntity.ok(userProfileService.getAllProfiles());
    }
}
