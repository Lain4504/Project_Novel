package com.backend.transactionservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserConsumeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    String status;
    String productType;
    Double productAmount;
    LocalDateTime createdDate;
    String novelId;
}
