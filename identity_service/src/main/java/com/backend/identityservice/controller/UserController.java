package com.backend.identityservice.controller;

import com.backend.identityservice.dto.request.UserCreationRequest;
import com.backend.identityservice.dto.request.UserUpdateRequest;
import com.backend.identityservice.dto.response.UserResponse;
import com.backend.identityservice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;
    @PostMapping("/registration")
    ResponseEntity<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
    @GetMapping
    ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @PutMapping("/{userId}")
    ResponseEntity<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
    @GetMapping("/my-info")
    ResponseEntity<UserResponse> getMyInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }
}
