package com.example.application.comment.event;

import com.example.application.account.mapper.AccountReadMapper;
import com.example.application.community.mapper.CommunityReadMapper;
import com.example.application.notification.dto.NotificationDto;
import com.example.application.notification.mapper.NotificationWriteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@Async
@RequiredArgsConstructor
public class CommentCreatedEventListener {

    private final CommunityReadMapper communityReadMapper;
    private final AccountReadMapper accountReadMapper;
    private final NotificationWriteMapper notificationWriteMapper;

    @EventListener
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {

        Map<String, Object> resultMap = communityReadMapper.selectAccountTitleByCommunityId(event.getComment().getCommunityId());

        Integer accountId = (Integer) resultMap.get("accountid");
        Long newAccountId = Long.valueOf(accountId);

        String title = (String) resultMap.get("title");

        boolean notificationEnabled = accountReadMapper.selectNotificationEnabled(newAccountId);
        if (notificationEnabled) {
            createNotification(event, newAccountId, title);
        }

    }




    private void createNotification(CommentCreatedEvent event, Long accountId, String title) {
        NotificationDto notification = new NotificationDto();
        notification.setMessage(title);
        notification.setLink("/article/" + event.getComment().getCommunityId());
        notification.setChecked(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setAccountId(accountId);
        notificationWriteMapper.insertNotification(notification);
    }
}

