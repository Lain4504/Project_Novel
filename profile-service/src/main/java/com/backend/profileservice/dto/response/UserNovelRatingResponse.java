package com.backend.profileservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNovelRatingResponse {
    String id;
    String userId;
    String novelId;
    Integer rating;
}
