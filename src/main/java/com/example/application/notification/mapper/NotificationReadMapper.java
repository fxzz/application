package com.example.application.notification.mapper;

import com.example.application.notification.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationReadMapper {
    List<NotificationDto> selectNotificationByAccountId(Long accountId);
    List<NotificationDto> selectReadNotificationByAccountId(Long accountId);
    Long selectNotificationCount(Long accountId);
}
