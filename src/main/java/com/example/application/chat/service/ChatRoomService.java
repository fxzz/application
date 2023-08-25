package com.example.application.chat.service;

import com.example.application.chat.dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {
    void saveChatRoom(ChatRoomDto chatRoomDto);
    List<ChatRoomDto> getAllChatRooms();
    void addChatListUser(String roomId, String userId);
    List<String> getChatUserList(String roomId);
    void removeChatListUser(String roomId, String userId);
}
