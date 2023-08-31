package com.example.application.account.mapper;

import com.example.application.account.dto.Account;
import com.example.application.account.dto.NotificationUpdateDto;
import org.apache.ibatis.annotations.Mapper;


import java.util.Map;

@Mapper
public interface AccountWriteMapper {

    void insertAccount(Account account);
    void updateProfile(Map map);
    void updatePassword(Account account);
    void updateNotificationEnabled(NotificationUpdateDto notificationUpdateDto);
}
