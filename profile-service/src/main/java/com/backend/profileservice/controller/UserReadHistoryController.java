package com.backend.profileservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.profileservice.entity.UserReadHistory;
import com.backend.profileservice.service.UserReadHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-read-history")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserReadHistoryController {
    UserReadHistoryService userReadHistoryService;
    @GetMapping("/all/{userId}")
    public ApiResponse<List<UserReadHistory>> getAllUserReadHistory(@PathVariable String userId, @RequestParam(value = "type", required = false) String type) {
        return ApiResponse.<List<UserReadHistory>>builder()
                .result(userReadHistoryService.getReadHistoryByUserId(userId, type))
                .build();
    }
    @PostMapping
    public ApiResponse<UserReadHistory> addReadHistory(@RequestBody UserReadHistory userReadHistory) {
        return ApiResponse.<UserReadHistory>builder()
                .result(userReadHistoryService.createOrUpdate(userReadHistory))
                .build();
    }
    @DeleteMapping("/{novelId}")
    public ApiResponse<Void> deleteReadHistory(@PathVariable String novelId) {
        userReadHistoryService.delete(novelId);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/has-read/{novelId}")
    public ApiResponse<Optional> hasReadNovel(@PathVariable String novelId) {
        return ApiResponse.<Optional>builder()
                .result(userReadHistoryService.getById(novelId))
                .build();
    }
}
