package com.backend.profileservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class UserNovelFollowResponse {
    @MongoId
    String id;
    String userId;
    String novelId;
    Instant createdDate;
    Instant modifiedDate;
}
