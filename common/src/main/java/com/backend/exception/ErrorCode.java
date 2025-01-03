package com.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("1001", "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED("1002", "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("1003", "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("1004", "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("1005", "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("1006", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("1007", "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB("1008", "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INCORRECT_CREDENTIALS("1009", "Incorrect email or password", HttpStatus.BAD_REQUEST),
    INACTIVE_USER("1010", "Account isn't active", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN("1011", "Refresh token isn't invalid", HttpStatus.BAD_REQUEST),
    FILE_NAME_IS_EMPTY("1012", "File name must not be null", HttpStatus.BAD_REQUEST),
    FILE_EXTENSION_INVALID("1013", "File extension is invalid", HttpStatus.BAD_REQUEST),
    FILE_SIZE_INVALID("1014", "File size is invalid", HttpStatus.BAD_REQUEST),
    IMAGE_NOT_FOUND("1015", "Image not found", HttpStatus.NOT_FOUND),
    INVALID_INPUT("1016", "Invalid input", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(String code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
