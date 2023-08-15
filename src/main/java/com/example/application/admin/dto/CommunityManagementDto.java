package com.example.application.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommunityManagementDto {
    private Long communityId;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private Long reportCount;
    private String deleted;
}
