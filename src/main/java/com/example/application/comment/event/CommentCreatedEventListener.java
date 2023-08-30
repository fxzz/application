package com.example.application.comment.event;

import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.community.mapper.CommunityReadMapper;
import com.example.application.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Async
@RequiredArgsConstructor
public class CommentCreatedEventListener {

    private final CommunityReadMapper communityReadMapper;
    private final NotificationService notificationService;

    @EventListener
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {

        Long accountId = communityReadMapper.selectAccountIdByCommunityId(event.getComment().getCommunityId());


    }
}

