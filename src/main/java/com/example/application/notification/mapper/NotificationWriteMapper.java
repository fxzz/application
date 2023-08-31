package com.example.application.notification.mapper;

import com.example.application.notification.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface NotificationWriteMapper {

   void insertNotification(NotificationDto notificationDto);
   void readNotification(Long id);
   void deleteReadNotificationsByAccountId(Long accountId);
}
