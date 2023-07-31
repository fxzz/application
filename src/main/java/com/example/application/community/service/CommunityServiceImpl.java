package com.example.application.community.service;

import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.dto.PageHandler;
import com.example.application.community.dto.SearchCondition;
import com.example.application.community.mapper.CommunityMapper;
import com.example.application.security.UserAccount;
import com.example.application.tag.service.TagService;
import com.example.application.util.exception.InvalidAccessOperationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityMapper communityMapper;
    private final ObjectMapper objectMapper;
    private final TagService tagService;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public void insertCommunity(CommunityNewDto communityNewRespDto) {
        communityMapper.insertCommunity(communityNewRespDto);
    }


    @Override
    public void saveCommunity(CommunityNewReqDto communityNewReqDto, Long accountId) {
        CommunityNewDto communityNewDto = CommunityNewDto.builder()
                .title(communityNewReqDto.getTitle())
                .content(communityNewReqDto.getContent())
                .accountId(accountId)
                .build();

        insertCommunity(communityNewDto);

        try {
            List<String> tagList = objectMapper.readValue(communityNewReqDto.getTag(), ArrayList.class);
            for (String tag : tagList) {
              int tagId = tagService.selectTagId(tag);
              Map<String, Integer> map = new HashMap<>();
              map.put("tagId", tagId);
              map.put("communityId",communityNewDto.getCommunityId());
              insertCommunityTag(map);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insertCommunityTag(Map map) {
        communityMapper.insertCommunityTag(map);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommunityTagResultDto> selectAllCommunityTag(SearchCondition searchCondition) {
        return communityMapper.selectAllCommunityTag(searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommunityTagResultDto> CommunityTagPagedLimitByKeyword(SearchCondition searchCondition) {
        return selectAllCommunityTag(searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public int searchResultCnt(SearchCondition searchCondition) {
        return communityMapper.searchResultCnt(searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public PageHandler createPageHandler(SearchCondition searchCondition) {
        int totalCnt = searchResultCnt(searchCondition);
        return new PageHandler(totalCnt, searchCondition);
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDto selectCommunityByCommunityId(Long communityId) {
        return communityMapper.selectCommunityByCommunityId(communityId);
    }


    @Override
    public ArticleDto getArticleById(Long communityId) {
        updateArticleView(communityId);
        return selectCommunityByCommunityId(communityId);
    }

    @Override
    public void deleteArticle(Map map) {
        communityMapper.deleteArticle(map);
    }

    @Override
    public void deleteArticleAction(Long communityId, Long accountId) {
        Map<String, Long> map = new HashMap<>();
        map.put("communityId", communityId);
        map.put("accountId", accountId);
        deleteCommunityTagsByCommunityId(communityId);
        deleteArticle(map);
    }

    @Override
    public void deleteCommunityTagsByCommunityId(Long communityId) {
        communityMapper.deleteCommunityTagsByCommunityId(communityId);
    }

    @Override
    public void updateArticleView(Long communityId) {
        communityMapper.updateArticleView(communityId);
    }


    @Transactional(readOnly = true)
    @Override
    public ArticleModificationFormDto selectArticleUpdateByCommunityId(Long communityId) {
        return communityMapper.selectArticleUpdateByCommunityId(communityId);
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleModificationFormDto getArticleModificationForm(Long communityId, UserAccount userAccount) {
        ArticleModificationFormDto articleModificationFormDto = selectArticleUpdateByCommunityId(communityId);
        if (!articleModificationFormDto.getAccountId().equals(userAccount.accountId())) {
            throw new InvalidAccessOperationException("잘못된 접근입니다.");
        }
        return articleModificationFormDto;
    }

    @Override
    public void modifyArticle(Integer communityId, ArticleModificationFormDto articleModificationFormDto) {
      ArticleModificationDto articleModificationDto = ArticleModificationDto.builder()
                                     .communityId(articleModificationFormDto.getCommunityId())
                                     .title(articleModificationFormDto.getTitle())
                                     .content(articleModificationFormDto.getContent())
                                     .modifiedAt(LocalDateTime.now())
                                     .build();

        deleteCommunityTagsByCommunityId(articleModificationFormDto.getCommunityId());
        updateArticle(articleModificationDto);

        try {
            List<String> tagList = objectMapper.readValue(articleModificationFormDto.getTag(), ArrayList.class);
            for (String tag : tagList) {
                int tagId = tagService.selectTagId(tag);
                Map<String, Integer> map = new HashMap<>();
                map.put("tagId", tagId);
                map.put("communityId",communityId);
                insertCommunityTag(map);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateArticle(ArticleModificationDto articleModificationDto) {
        communityMapper.updateArticle(articleModificationDto);
    }

}
