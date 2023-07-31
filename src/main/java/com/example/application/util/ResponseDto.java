package com.example.application.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {
    private final ErrorCode code;
    private final String msg;
    private final T data;
}
