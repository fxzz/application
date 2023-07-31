package com.example.application.community.service;

import com.example.application.community.dto.CommunityDto;
import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.dto.PageHandler;
import com.example.application.community.dto.SearchCondition;
import com.example.application.security.UserAccount;

import java.util.List;
import java.util.Map;

public interface CommunityService {
    void insertCommunity(CommunityNewDto communityNewRespDto);
    void saveCommunity(CommunityNewReqDto communityNewReqDto, Long accountId);
    void insertCommunityTag(Map map);
    List<CommunityTagResultDto> selectAllCommunityTag(SearchCondition searchCondition);
    List<CommunityTagResultDto> CommunityTagPagedLimitByKeyword(SearchCondition searchCondition);
    int searchResultCnt(SearchCondition searchCondition);
    PageHandler createPageHandler(SearchCondition searchCondition);
    ArticleDto selectCommunityByCommunityId(Long communityId);
    ArticleDto getArticleById(Long communityId);
    void deleteArticle(Map map);
    void deleteArticleAction(Long communityId, Long accountId);
    void deleteCommunityTagsByCommunityId(Long communityId);
    void updateArticleView(Long communityId);
    ArticleModificationFormDto selectArticleUpdateByCommunityId(Long communityId);
    ArticleModificationFormDto getArticleModificationForm(Long communityId, UserAccount userAccount);
    void modifyArticle(Integer communityId, ArticleModificationFormDto articleModificationFormDto);
    void updateArticle(ArticleModificationDto articleModificationDto);
}
