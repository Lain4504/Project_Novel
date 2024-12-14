package com.backend.profileservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.profileservice.dto.request.UserNovelFollowRequest;
import com.backend.profileservice.dto.response.UserNovelFollowResponse;
import com.backend.profileservice.repository.httpclient.NovelDetailsResponse;
import com.backend.profileservice.service.UserNovelFollowService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-novel-follow")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserNovelFollowController {
    UserNovelFollowService userNovelFollowService;
    @PostMapping("/follow")
    ApiResponse<UserNovelFollowResponse> followNovel(@RequestBody UserNovelFollowRequest request) {
        return ApiResponse.<UserNovelFollowResponse>builder()
                .result(userNovelFollowService.followNovel(request))
                .build();
    }
    @PostMapping("/unfollow")
    ApiResponse<Void> unfollowNovel(@RequestBody UserNovelFollowRequest request) {
        userNovelFollowService.unfollowNovel(request);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/is-following")
    ApiResponse<Boolean> isFollowingNovel(@RequestBody UserNovelFollowRequest request) {
        return ApiResponse.<Boolean>builder()
                .result(userNovelFollowService.isFollowingNovel(request))
                .build();
    }
    @GetMapping("/get-my-following")
    ApiResponse<List<NovelDetailsResponse>> getFollowingNovelDetails(@RequestParam String userId) {
        return ApiResponse.<List<NovelDetailsResponse>>builder()
                .result(userNovelFollowService.getFollowingNovelsWithDetails(userId))
                .build();
    }

}
