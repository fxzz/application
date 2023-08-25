package com.example.application.chat.service;

import java.util.List;

public interface ChatUserListDatabaseService {

    List<String> getChatUserList(String roomId);
    void addChatListUser(String roomId, String userId);
    void removeChatListUser(String roomId, String userId);
}
