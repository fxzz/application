package com.example.application.notification.controller;

import com.example.application.account.dto.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    @GetMapping("notifications")
    public String getNotifications(Model model) {
        return "notification/list";
    }
}
