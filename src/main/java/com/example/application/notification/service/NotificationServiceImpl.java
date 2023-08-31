package com.example.application.notification.service;

import com.example.application.notification.dto.NotificationDto;
import com.example.application.notification.mapper.NotificationReadMapper;
import com.example.application.notification.mapper.NotificationWriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationReadMapper notificationReadMapper;
    private final NotificationWriteMapper notificationWriteMapper;

    @Transactional(readOnly = true)
    @Override
    public List<NotificationDto> selectNotificationByAccountId(Long accountId) {
        return notificationReadMapper.selectNotificationByAccountId(accountId);
    }

    @Transactional
    @Override
    public void readNotification(Long id) {
        notificationWriteMapper.readNotification(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationDto> selectReadNotificationByAccountId(Long accountId) {
        return notificationReadMapper.selectReadNotificationByAccountId(accountId);
    }

    @Transactional
    @Override
    public void deleteReadNotificationsByAccountId(Long accountId) {
        notificationWriteMapper.deleteReadNotificationsByAccountId(accountId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long selectNotificationCount(Long accountId) {
        return notificationReadMapper.selectNotificationCount(accountId);
    }
}
