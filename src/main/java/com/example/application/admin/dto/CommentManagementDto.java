package com.example.application.admin.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentManagementDto {
    private Long commentId;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private Long reportCount;
    private String deleted;
}
