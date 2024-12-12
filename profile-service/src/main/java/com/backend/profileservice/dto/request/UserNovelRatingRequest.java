package com.backend.profileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNovelRatingRequest {
     String userId;
     String novelId;
     Integer rating;
}
