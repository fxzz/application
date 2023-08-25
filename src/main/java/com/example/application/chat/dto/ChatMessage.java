package com.example.application.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;

    public enum MessageType {
        CHAT, // 일반 채팅 메시지
        JOIN, // 사용자 입장 메시지
        LEAVE // 사용자 퇴장 메시지
    }
}