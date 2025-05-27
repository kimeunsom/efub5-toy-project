package com.example.efubtoy.global.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "Method Not Allowed"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C003", "Access Denied"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "Invalid Type Value"),

    // User related errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User not found"),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "U002", "Unauthorized User"), // 비밀번호 불일치 등

    // Tweet related errors
    TWEET_NOT_FOUND(HttpStatus.NOT_FOUND, "T001", "Tweet not found"),
    TWEET_AUTHOR_MISMATCH(HttpStatus.FORBIDDEN, "T002", "Tweet author mismatch"), // 작성자가 아닐 때

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}