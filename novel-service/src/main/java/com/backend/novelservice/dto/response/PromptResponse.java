package com.backend.novelservice.dto.response;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromptResponse {
    String id;
    String imageUrl;
    String redirectUrl;
    String title;
    String description;
    String type;
    String status;
    Instant createdBy;
    Instant updatedBy;
    String created;
}
