package com.example.application.admin.controller;

import com.example.application.admin.dto.*;
import com.example.application.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String admin(Model model) {
      List<Integer> counts = adminService.getTodayCounts();
        model.addAttribute("accountCount", counts.get(0));
        model.addAttribute("communityCount", counts.get(1));
        model.addAttribute("commentCount", counts.get(2));

        WeeklyAccountCountsDto weeklyAccountCounts = adminService.getWeeklyAccountCounts();
        model.addAttribute("weeklyAccountCounts", weeklyAccountCounts);
        log.info("weeklyAccountCounts {}", weeklyAccountCounts);
        return "admin/index";
    }

    @GetMapping("/community-management")
    public String communityManagementTable(Model model, SearchCondition searchCondition) {
        List<CommunityManagementDto> communityManagementList = adminService.getCommunityManagementList(searchCondition);
        PageHandler ph = adminService.createCommunityPageHandler(searchCondition);

        model.addAttribute("communityManagementList", communityManagementList);
        model.addAttribute("ph", ph);
        return "admin/communityTables";
    }

    @GetMapping("/comment-management")
    public String commentManagementTable(Model model, SearchCondition searchCondition) {
        List<CommentManagementDto> commentManagementList = adminService.getCommentManagementList(searchCondition);
        PageHandler ph = adminService.createCommentPageHandler(searchCondition);

        model.addAttribute("commentManagementList", commentManagementList);
        model.addAttribute("ph", ph);
        return "admin/commentTables";
    }

    @PostMapping("/community-management/{communityId}/delete")
    public String removeCommunityManagement(@PathVariable Long communityId) {
        adminService.removeCommunityManagement(communityId);
        return "redirect:/community-management";
    }

    @PostMapping("/comment-management/{commentId}/delete")
    public String removeCommentManagement(@PathVariable Long commentId) {
        adminService.removeCommentManagement(commentId);
        return "redirect:/comment-management";
    }

    @PostMapping("/community-management/{nickname}/block")
    public String blockAccountManagement(@PathVariable String nickname) {
        adminService.blockAccount(nickname);
        return "redirect:/community-management";
    }

    @GetMapping("/block")
    public String blockAccount() {
        return "block";
    }
}
