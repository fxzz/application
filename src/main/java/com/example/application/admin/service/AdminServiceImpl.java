package com.example.application.admin.service;

import com.example.application.account.dto.Account;
import com.example.application.admin.dto.*;
import com.example.application.admin.mapper.AdminMapper;


import com.example.application.security.UserAccount;
import com.example.application.util.SessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final SessionRegistry sessionRegistry;

    @Transactional(readOnly = true)
    @Override
    public List<CommunityManagementDto> getCommunityManagementList(SearchCondition searchCondition) {
       return adminMapper.selectCommunityManagement(searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public PageHandler createCommunityPageHandler(SearchCondition searchCondition) {
        int totalCnt = adminMapper.searchCommunityResultCnt(searchCondition);
        return new PageHandler(totalCnt, searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentManagementDto> getCommentManagementList(SearchCondition searchCondition) {
        return adminMapper.selectCommentManagement(searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public PageHandler createCommentPageHandler(SearchCondition searchCondition) {
        int totalCnt = adminMapper.searchCommentResultCnt(searchCondition);
        return new PageHandler(totalCnt, searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Integer> getTodayCounts() {
        List<Integer> counts = new ArrayList<>();
        counts.add(adminMapper.selectCountTodayAccount());
        counts.add(adminMapper.selectCountTodayCommunity());
        counts.add(adminMapper.selectCountTodayComment());

        return counts;
    }

    @Transactional(readOnly = true)
    @Override
    public WeeklyAccountCountsDto getWeeklyAccountCounts() {
       return adminMapper.selectWeeklyAccountCounts();
    }

    @Transactional
    @Override
    public void removeCommunityManagement(Long communityId) {
         adminMapper.deleteCommunityManagement(communityId);
    }

    @Transactional
    @Override
    public void removeCommentManagement(Long commentId) {
        adminMapper.deleteCommentManagement(commentId);
    }

    @Transactional
    @Override
    public void blockAccount(String nickname) {
        adminMapper.blockAccount(nickname);

        SessionUtils.expireSessionsByNickname(nickname, sessionRegistry);

    }


}
