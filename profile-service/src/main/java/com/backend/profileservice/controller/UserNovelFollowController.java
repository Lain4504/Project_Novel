package com.backend.profileservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.profileservice.dto.request.UserNovelFollowRequest;
import com.backend.profileservice.dto.response.UserNovelFollowResponse;
import com.backend.profileservice.service.UserNovelFollowService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-novel-follow")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserNovelFollowController {
    UserNovelFollowService userNovelFollowService;
    @PostMapping("/follow")
    public ApiResponse<UserNovelFollowResponse> followNovel(@RequestBody UserNovelFollowRequest request) {
        userNovelFollowService.followNovel(request);
        return ApiResponse.<UserNovelFollowResponse>builder().build();
    }
    @PostMapping("/unfollow")
    public ApiResponse<Void> unfollowNovel(@RequestBody UserNovelFollowRequest request) {
        userNovelFollowService.unfollowNovel(request);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/is-following")
    public ApiResponse<Boolean> isFollowingNovel(@RequestBody UserNovelFollowRequest request) {
        return ApiResponse.<Boolean>builder().result(userNovelFollowService.isFollowingNovel(request)).build();
    }
}
