package com.backend.profileservice.controller;

import java.util.List;

import com.backend.dto.response.ApiResponse;
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
@RequestMapping("/users")
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/{userId}")
    ApiResponse<UserProfileResponse> getUserProfile(@PathVariable String userId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfile(userId)).
                build();
    }

    @DeleteMapping("/{profileId}")
    ApiResponse<Void> deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteProfile(profileId);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/{profileId}")
    ApiResponse<UserProfileResponse> updateUserProfile(
            @PathVariable String profileId, @RequestBody UserProfileUpdateRequest request) {
      return ApiResponse.<UserProfileResponse>builder()
              .result(userProfileService.updateProfile(profileId, request)).build();
    }

    @GetMapping("/getall")
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder().result(userProfileService.getAllProfiles()).build();
    }
}
