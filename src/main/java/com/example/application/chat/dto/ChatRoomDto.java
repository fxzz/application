package com.example.application.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class ChatRoomDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String nickname;
    private LocalDateTime createdAt;

    private ChatRoomDto(String id, String title, String nickname) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
        this.createdAt = LocalDateTime.now();
    }

    public static ChatRoomDto createNew(String title, String nickname) {
        return new ChatRoomDto(UUID.randomUUID().toString(), title, nickname);
    }


}