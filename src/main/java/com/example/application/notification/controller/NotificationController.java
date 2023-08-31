package com.example.application.notification.controller;

import com.example.application.account.dto.Account;
import com.example.application.notification.dto.NotificationDto;
import com.example.application.notification.service.NotificationService;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification")
    public String getNotifications(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        List<NotificationDto> notification = notificationService.selectNotificationByAccountId(userAccount.accountId());
        model.addAttribute("notification", notification);
        model.addAttribute("count", notification.size());
        return "notification/list";
    }

    @PostMapping("/notification")
    public String readNotifications(Long id, String link) {
        notificationService.readNotification(id);
        return "redirect:" + link;
    }

    @GetMapping("/read-notification")
    public String getReadNotifications(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        List<NotificationDto> notification = notificationService.selectReadNotificationByAccountId(userAccount.accountId());
        model.addAttribute("notification", notification);
        model.addAttribute("count", notification.size());
        return "notification/read";
    }

    @PostMapping("/delete-notification")
    public String deleteNotifications(@AuthenticationPrincipal UserAccount userAccount) {
        notificationService.deleteReadNotificationsByAccountId(userAccount.accountId());
        return "redirect:/notification";
    }
}
