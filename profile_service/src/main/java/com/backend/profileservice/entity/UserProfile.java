package com.backend.profileservice.entity;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(value = "user_profile")
public class UserProfile {
    @MongoId
    String id;
    String userId;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String ward;
    String district;
    String province;
}
