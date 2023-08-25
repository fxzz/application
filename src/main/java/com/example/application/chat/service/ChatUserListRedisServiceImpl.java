package com.example.application.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.example.application.chat.config.RedisConfig.EXPIRATION_DAYS;


@RequiredArgsConstructor
@Service
@Slf4j
public class ChatUserListRedisServiceImpl implements ChatUserListDatabaseService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public List<String> getChatUserList(String roomId) {
        String userListKey = getUserListKey(roomId);
        Set<String> users = redisTemplate.opsForSet().members(userListKey);

        List<String> userList = new ArrayList<>(users);
        return userList;
    }
    @Override
    public void addChatListUser(String roomId, String userId) {
        String userListKey = getUserListKey(roomId);
        redisTemplate.opsForSet().add(userListKey, userId);
        redisTemplate.expire(userListKey, EXPIRATION_DAYS, TimeUnit.DAYS);
    }
    @Override
    public void removeChatListUser(String roomId, String userId) {
        String userListKey = getUserListKey(roomId);
        redisTemplate.opsForSet().remove(userListKey, userId);
    }

    private String getUserListKey(String roomId) {
        return "chat:" + roomId + ":users";
    }
}
