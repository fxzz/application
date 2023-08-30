package com.example.application.notification.dto;

import com.example.application.account.dto.Account;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationDto {
    private String title;
    private String content;
    private String link;
    private boolean checked;
    private Long accountId;
    private LocalDateTime createdAt;
}
