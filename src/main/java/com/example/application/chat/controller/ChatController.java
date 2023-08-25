package com.example.application.chat.controller;

import com.example.application.chat.dto.ChatMessage;
import com.example.application.chat.service.ChatRoomService;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chat/{roomId}")
    public String enterChatRoom(@PathVariable String roomId, @RequestParam(required = false) String title, Model model, @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("nickname", userAccount.nickname());
        model.addAttribute("title", title);
        return "chat/chat";
    }

    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat/{roomId}/join")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage joinRoom(@DestinationVariable String roomId, ChatMessage welcomeMessage, Authentication authentication) {
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        welcomeMessage.setSender(userAccount.nickname());
        welcomeMessage.setContent("님이 입장하셨습니다.");
        chatRoomService.addChatListUser(roomId, userAccount.nickname());
        return welcomeMessage;
    }

    @GetMapping("/chat/{roomId}/users")
    @ResponseBody
    public List<String> chatUserList(@PathVariable String roomId) {
        List<String> chatList = chatRoomService.getChatUserList(roomId);
        return chatList;
    }

    @PostMapping("/chat/{roomId}/users")
    @ResponseBody
    public void chatUserRemove(@PathVariable String roomId, @RequestBody Map<String, String> user) {
        String userId = user.get("userId");
        chatRoomService.removeChatListUser(roomId, userId);
        log.info("userId: {}", userId);
    }

    @MessageMapping("/chat/{roomId}/remove")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage removeRoom(ChatMessage removeMessage, Authentication authentication) {
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        removeMessage.setSender(userAccount.nickname());
        removeMessage.setContent("님이 퇴장하셨습니다.");
        return removeMessage;
    }

}