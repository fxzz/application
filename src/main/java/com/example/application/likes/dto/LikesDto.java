package com.example.application.likes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class LikesDto {
    private Long communityId;
    private Long accountId;
    private Long likesCount;
    private Long change;
}
