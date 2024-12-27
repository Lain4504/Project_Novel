package com.backend.novelservice.dto.response;

import lombok.*;

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
    String createdBy;
    String updatedBy;
}
