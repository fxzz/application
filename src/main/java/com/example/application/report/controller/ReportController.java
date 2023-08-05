package com.example.application.report.controller;

import com.example.application.report.service.ReportService;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report/community/{communityId}")
    public ResponseEntity<String> reportCommunity(@PathVariable Long communityId, @AuthenticationPrincipal UserAccount userAccount) {
        reportService.reportCommunity(communityId, userAccount.accountId());
        return ResponseEntity.ok("OK.");
    }

    @PostMapping("/report/comment/{commentId}")
    public ResponseEntity<String> reportComment(@PathVariable Long commentId, @AuthenticationPrincipal UserAccount userAccount) {
        reportService.reportComment(commentId, userAccount.accountId());
        return ResponseEntity.ok("OK.");
    }

}
