package com.example.application.chat.service;

import com.example.application.chat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomDatabaseService chatRoomDatabaseService;
    private final ChatUserListDatabaseService chatUserListDatabaseService;


    @Override
    public void saveChatRoom(ChatRoomDto chatRoomDto) {
        chatRoomDatabaseService.saveChatRoom(chatRoomDto);
    }


    @Override
    public List<ChatRoomDto> getAllChatRooms() {
        return chatRoomDatabaseService.getAllChatRooms();
    }


    @Override
    public void addChatListUser(String roomId, String userId) {
        chatUserListDatabaseService.addChatListUser(roomId, userId);
    }


    @Override
    public List<String> getChatUserList(String roomId) {
        return chatUserListDatabaseService.getChatUserList(roomId);
    }


    @Override
    public void removeChatListUser(String roomId, String userId) {
        chatUserListDatabaseService.removeChatListUser(roomId, userId);
    }
}
