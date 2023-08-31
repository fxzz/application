package com.example.application.notification.service;

import com.example.application.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> selectNotificationByAccountId(Long accountId);
    void readNotification(Long id);
    List<NotificationDto> selectReadNotificationByAccountId(Long accountId);
    void deleteReadNotificationsByAccountId(Long accountId);
    Long selectNotificationCount(Long accountId);
}
