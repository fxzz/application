package com.example.application.admin.service;

import com.example.application.admin.dto.*;

import java.util.List;

public interface AdminService {
    List<CommunityManagementDto> getCommunityManagementList(SearchCondition searchCondition);
    PageHandler createCommunityPageHandler(SearchCondition searchCondition);
    List<CommentManagementDto> getCommentManagementList(SearchCondition searchCondition);
    PageHandler createCommentPageHandler(SearchCondition searchCondition);
    List<Integer> getTodayCounts();
    WeeklyAccountCountsDto getWeeklyAccountCounts();
    void removeCommunityManagement(Long communityId);
    void removeCommentManagement(Long commentId);
    void blockAccount(String nickname);
}
