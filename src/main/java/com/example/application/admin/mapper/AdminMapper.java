package com.example.application.admin.mapper;

import com.example.application.admin.dto.CommentManagementDto;
import com.example.application.admin.dto.CommunityManagementDto;
import com.example.application.admin.dto.SearchCondition;
import com.example.application.admin.dto.WeeklyAccountCountsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<CommunityManagementDto> selectCommunityManagement(SearchCondition searchCondition);
    int searchCommunityResultCnt(SearchCondition searchCondition);
    List<CommentManagementDto> selectCommentManagement(SearchCondition searchCondition);
    int searchCommentResultCnt(SearchCondition searchCondition);
    Integer selectCountTodayAccount();
    Integer selectCountTodayCommunity();
    Integer selectCountTodayComment();
    WeeklyAccountCountsDto selectWeeklyAccountCounts();
    void deleteCommunityManagement(Long communityId);
    void deleteCommentManagement(Long commentId);
    void blockAccount(String nickname);
}
