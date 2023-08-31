package com.example.application.notification.dto;

import com.example.application.account.dto.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NotificationDto {
    private Long id;
    private String message;
    private String link;
    private boolean checked;
    private Long accountId;
    private LocalDateTime createdAt;
}
