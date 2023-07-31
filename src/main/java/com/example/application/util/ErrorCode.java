package com.example.application.util;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(1),
    FAILURE(-1);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

}
