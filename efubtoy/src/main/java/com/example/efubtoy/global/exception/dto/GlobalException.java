package com.example.efubtoy.global.exception.dto;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}