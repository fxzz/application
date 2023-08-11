package com.example.application.community.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RankIngLikesDto {
    private Long communityId;
    private String title;
    private Long likes;
    private LocalDateTime createdAt;
}