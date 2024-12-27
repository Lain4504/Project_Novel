package com.backend.novelservice.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromptRequest {
    String imageUrl;
    String redirectUrl;
    String title;
    String description;
    String type;
    String status;
    String createdBy;
    String updatedBy;
}
