package com.example.application.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursorResponse<T> {
    private CursorRequest nextCursorRequest;
    List<T> content;
}
