package com.example.application.chat.service;

import com.example.application.chat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.example.application.chat.config.RedisConfig.EXPIRATION_DAYS;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomRedisServiceImpl implements ChatRoomDatabaseService {


    private final RedisTemplate<String, ChatRoomDto> redisTemplate;


    private static final String CHAT_ROOM_PREFIX = "chatRoom:";


    @Override
    public void saveChatRoom(ChatRoomDto chatRoomDto) {
        redisTemplate.opsForValue().set(CHAT_ROOM_PREFIX + chatRoomDto.getId(), chatRoomDto);
        redisTemplate.expire(CHAT_ROOM_PREFIX + chatRoomDto.getId(), EXPIRATION_DAYS, TimeUnit.DAYS);
    }

    @Override
    public List<ChatRoomDto> getAllChatRooms() {
        Set<String> chatRoomKeys = redisTemplate.keys(CHAT_ROOM_PREFIX + "*");

        List<ChatRoomDto> chatRooms = new ArrayList<>();
        for (String key : chatRoomKeys) {
            ChatRoomDto chatRoomDto = redisTemplate.opsForValue().get(key);
            chatRooms.add(chatRoomDto);
        }

        return chatRooms;
    }


}
