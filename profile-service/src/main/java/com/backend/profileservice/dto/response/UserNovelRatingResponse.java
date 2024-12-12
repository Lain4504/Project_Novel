package com.backend.profileservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNovelRatingResponse {
    String id;
    String userId;
    String novelId;
    Integer rating;
}
