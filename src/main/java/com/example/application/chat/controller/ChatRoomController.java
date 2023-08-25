package com.example.application.chat.controller;


import com.example.application.chat.dto.ChatRoomDto;
import com.example.application.chat.service.ChatRoomService;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.WebSocketMessage;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;




    @GetMapping("/chatroom")
    public String chatRoom(Model model) {
        List<ChatRoomDto> chatRooms = chatRoomService.getAllChatRooms();
        model.addAttribute("chatRooms", chatRooms);
        log.info("chatRooms {}", chatRooms);
        return "chat/chatRoom";
    }

    @GetMapping("/chat/create")
    public String createChatRoomPage() {
        return "chat/createChatRoom";
    }

    @PostMapping("/chat/create")
    public String createChatRoom(@RequestParam String title, Model model, @AuthenticationPrincipal UserAccount userAccount) {
        ChatRoomDto chatRoom = ChatRoomDto.createNew(title, userAccount.nickname());
        chatRoomService.saveChatRoom(chatRoom);
        model.addAttribute("message", "채팅방이 생성되었습니다.");

        return "redirect:/chatroom";
    }




}
