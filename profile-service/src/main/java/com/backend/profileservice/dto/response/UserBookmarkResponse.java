package com.backend.profileservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserBookmarkResponse {
    String id;
    String userId;
    String novelId;
    String novelTitle;
    String novelChapterId;
    String novelChapterTitle;
    String contentNote;
    String created;
}
