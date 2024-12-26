package com.backend.profileservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserReviewResponse {
    String id;
    String userId;
    String userName;
    String novelId;
    String review;
    String created;
    String novelName;
    String image;
}
