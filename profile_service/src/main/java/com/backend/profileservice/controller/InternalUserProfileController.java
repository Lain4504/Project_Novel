package com.backend.profileservice.controller;

import org.springframework.web.bind.annotation.*;

import com.backend.profileservice.Service.UserProfileService;
import com.backend.profileservice.dto.request.ProfileCreationRequest;
import com.backend.profileservice.dto.response.UserProfileResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/internal/users")
    UserProfileResponse createUserProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }
}
