package com.backend.profileservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.profileservice.dto.request.UserNovelRatingRequest;
import com.backend.profileservice.dto.response.UserNovelRatingResponse;
import com.backend.profileservice.service.UserNovelRatingService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-novel-rating")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserNovelRatingController {
    UserNovelRatingService userNovelRatingService;
    @PostMapping("/rate")
    public ApiResponse<UserNovelRatingResponse> rateNovel(UserNovelRatingRequest request) {
        return ApiResponse.<UserNovelRatingResponse>builder()
                .result(userNovelRatingService.rateNovel(request))
                .build();
    }
    @DeleteMapping("/unrate")
    public ApiResponse<Void> unrateNovel(UserNovelRatingRequest request) {
        userNovelRatingService.deleteRating(request);
        return ApiResponse.<Void>builder().build();
    }
    @PutMapping("/update")
    public ApiResponse<UserNovelRatingResponse> updateRating(UserNovelRatingRequest request) {
        return ApiResponse.<UserNovelRatingResponse>builder()
                .result(userNovelRatingService.updateRating(request))
                .build();
    }
}
