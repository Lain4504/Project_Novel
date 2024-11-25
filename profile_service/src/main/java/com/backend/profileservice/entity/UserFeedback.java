package com.backend.profileservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(value = "user_feedback")
public class UserFeedback {
    @MongoId
    String id;
    String userId;
    String feedback;
    String feedbackType;
    String feedbackStatus;
    String feedbackResponse;
    String feedbackResponseBy;
    String feedbackResponseDate;
    String feedbackResponseTime;
    String feedbackDate;

}
