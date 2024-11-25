package com.backend.transactionservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String Id;
    String userId;
    Double balance;

}
