package com.backend.commentservice.repository.httpclient;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class UserProfileResponse {
    private String id;
    private String userId;
    private String username;
    private LocalDate dateOfBirth;
    private String bio;
    private String gender;
    private ImageResponse image;
    private String created;
    private Instant createdAt;

    @Data
    public static class ImageResponse {
        private String path;
    }
}
