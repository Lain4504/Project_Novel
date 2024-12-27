package com.backend.profileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserReadHistoryRequest {
    String userId;
    String novelId;
    String novelTitle;
    String novelChapterId;
    String novelChapterTitle;
}
