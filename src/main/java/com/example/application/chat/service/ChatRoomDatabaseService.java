package com.example.application.chat.service;

import com.example.application.chat.dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomDatabaseService {
    void saveChatRoom(ChatRoomDto chatRoomDto);
    List<ChatRoomDto> getAllChatRooms();
}
