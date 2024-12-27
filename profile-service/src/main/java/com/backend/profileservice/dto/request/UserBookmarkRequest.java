package com.backend.profileservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@Setter
public class UserBookmarkRequest {
    String id;
    String userId;
    String novelId;
    String novelTitle;
    String novelChapterId;
    String novelChapterTitle;
    String contentNote;
}
