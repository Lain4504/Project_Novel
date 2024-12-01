package com.backend.transactionservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPayLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    Double amount;
    String status;
    LocalDateTime createdDate;
    String novelId;
    String chapterId;
}
