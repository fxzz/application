package com.example.application.community.service;


import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.dto.PageHandler;
import com.example.application.community.dto.SearchCondition;
import com.example.application.security.UserAccount;

import java.util.List;


public interface CommunityService {

    void saveCommunity(CommunityNewReqDto communityNewReqDto, Long accountId);
    List<CommunityTagResultDto> CommunityTagPagedLimitByKeyword(SearchCondition searchCondition);
    PageHandler createPageHandler(SearchCondition searchCondition);
    ArticleDto getArticleById(Long communityId);
    void deleteArticleAction(Long communityId, Long accountId);
    void updateArticleView(Long communityId);
    ArticleModificationFormDto getArticleModificationForm(Long communityId, UserAccount userAccount);
    void modifyArticle(Integer communityId, ArticleModificationFormDto articleModificationFormDto);
    void updateArticle(ArticleModificationDto articleModificationDto);
}
