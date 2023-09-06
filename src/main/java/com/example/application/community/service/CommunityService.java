package com.example.application.community.service;


import com.example.application.community.dto.*;
import com.example.application.community.dto.CommunityDto.*;
import com.example.application.security.UserAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface CommunityService {

    void saveCommunity(CommunityNewReqDto communityNewReqDto, Long accountId, MultipartFile[] files, String uploadPath) throws IOException;
    List<CommunityTagResultDto> CommunityTagPagedLimitByKeyword(SearchCondition searchCondition);
    PageHandler createPageHandler(SearchCondition searchCondition);
    ArticleDto getArticleById(Long communityId);
    void deleteArticleAction(Long communityId, Long accountId);
    void updateArticleView(Long communityId);
    ArticleModificationFormDto getArticleModificationForm(Long communityId, UserAccount userAccount);
    void modifyArticle(Integer communityId, ArticleModificationFormDto articleModificationFormDto, MultipartFile[] files, String uploadPath) throws IOException;
    void updateArticle(ArticleModificationDto articleModificationDto);
    void updateCommunityImageEnabled(CommunityImageEnabledDto communityImageEnabledDto);

    CursorResponse<CursorDto> getCursorPage(String nickname, CursorRequest cursorRequest);
}
