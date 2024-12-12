package com.backend.profileservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.profileservice.dto.request.UserReviewRequest;
import com.backend.profileservice.dto.response.UserReviewResponse;
import com.backend.profileservice.service.UserReviewService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NovelReviewController {
    UserReviewService userReviewService;
    @PostMapping("/novel")
    public ApiResponse<UserReviewResponse> createReview(@RequestBody UserReviewRequest request) {
        return ApiResponse.<UserReviewResponse>builder().result(userReviewService.createReview(request)).build();
    }
    @PutMapping("/update")
    public ApiResponse<UserReviewResponse> updateReview(@RequestBody UserReviewRequest request) {
        return ApiResponse.<UserReviewResponse>builder().result(userReviewService.updateReview(request)).build();
    }
    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteReview(@RequestBody UserReviewRequest request) {
        userReviewService.deleteReview(request);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/novel/{novelId}")
    public ApiResponse<PageResponse<UserReviewResponse>> getReviewsByNovelId(@PathVariable Long novelId,
                                                                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<UserReviewResponse>>builder()
                .result(userReviewService.getReviewsByNovelId(novelId, page, size))
                .build();
    }
}
