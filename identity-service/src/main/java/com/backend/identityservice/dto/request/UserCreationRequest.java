package com.backend.identityservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Email(message = "INVALID_EMAIL")
    @NotNull(message = "EMAIL_REQUIRED")
    String email;
    @Size(min = 8, message = "INVALID_PASSWORD")
    @NotNull(message = "PASSWORD_REQUIRED")
    String password;
}
