package com.example.application.report.service;

public interface ReportService {
   void reportCommunity(Long communityId, Long accountId);
   void reportComment(Long commentId, Long accountId);
}
