package com.backend.identityservice.entity;

import com.backend.identityservice.enums.UserState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.mapstruct.EnumMapping;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String email;
    String password;
    LocalDate createdDate;
    @Enumerated(EnumType.STRING)
    UserState state;
    @ManyToMany
    Set<Role> roles;
}
