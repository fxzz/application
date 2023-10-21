package com.example.application.community.service;

import com.example.application.account.mapper.AccountReadMapper;
import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.dto.*;
import com.example.application.community.mapper.CommunityReadMapper;
import com.example.application.community.mapper.CommunityWriteMapper;
import com.example.application.security.UserAccount;
import com.example.application.tag.service.TagService;
import com.example.application.util.exception.CommunityIdValidationException;
import com.example.application.util.exception.UnauthorizedAccessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceImpl implements CommunityService {

    private final CommunityWriteMapper communityWriteMapper;
    private final CommunityReadMapper communityReadMapper;
    private final ObjectMapper objectMapper;
    private final TagService tagService;
    private final AccountReadMapper accountReadMapper;
    private final ApplicationEventPublisher eventPublisher;




    @Override
    public void saveCommunity(CommunityNewReqDto communityNewReqDto, Long accountId, MultipartFile[] files, String uploadPath) {
        CommunityNewDto communityNewDto = createCommunity(communityNewReqDto, accountId);

        if (files != null) {
            eventPublisher.publishEvent(FileUploadEvent.createEvent(files, communityNewDto.getCommunityId().longValue(), uploadPath));
        }

        addTag(communityNewReqDto.getTag(), communityNewDto.getCommunityId());
    }



    @Transactional(readOnly = true)
    @Override
    public List<CommunityTagResultDto> getCommunityAll(SearchCondition searchCondition) {
        return communityReadMapper.selectAllCommunityTag(searchCondition);
    }


    @Transactional(readOnly = true)
    @Override
    public PageHandler createPageHandler(SearchCondition searchCondition) {
        int totalCnt = communityReadMapper.searchResultCnt(searchCondition);
        return new PageHandler(totalCnt, searchCondition);
    }



    @Transactional(readOnly = true)
    @Override
    public ArticleDto getArticleById(Long communityId) {

        ArticleDto articleDto = communityReadMapper.selectCommunityByCommunityId(communityId);

        if (articleDto == null) {
         throw new CommunityIdValidationException("communityId is null");
        }

        return articleDto;
    }



    @Override
    public void deleteArticleAction(Long communityId, Long accountId) {
        deleteArticle(communityId, accountId);
        communityWriteMapper.deleteCommunityTagsByCommunityId(communityId);
    }



    @Override
    public void updateArticleView(Long communityId) {
        communityWriteMapper.updateArticleView(communityId);
    }





    @Transactional(readOnly = true)
    @Override
    public ArticleModificationFormDto getArticleModifyForm(Long communityId, UserAccount userAccount) {
        return loadArticleData(communityId, userAccount);
    }


    @Override
    public void modifyArticle(Integer communityId, ArticleModificationFormDto articleModificationFormDto, MultipartFile[] files, String uploadPath) {
        updateArticle(articleModificationFormDto);

        communityWriteMapper.deleteCommunityTagsByCommunityId(articleModificationFormDto.getCommunityId());

        if (files != null) {
            eventPublisher.publishEvent(FileUploadEvent.createEvent(files, communityId.longValue(), uploadPath));
        }

        addTag(articleModificationFormDto.getTag(), communityId);


    }

    @Transactional(readOnly = true)
    @Override
    public CursorResponse<CursorDto> getCursorPage(String nickname, CursorRequest cursorRequest) {
        Long accountId = accountReadMapper.selectAccountIdByNickname(nickname);
        return getPageByAccountId(accountId, cursorRequest);
    }

    // nickname 으로 accountId 커버링 인덱스로 찾고 getAccountPage 호출
    private CursorResponse<CursorDto> getPageByAccountId(Long accountId, CursorRequest cursorRequest) {
        List<CursorDto> cursorDtos = selectAllPageByAccountId(accountId, cursorRequest);
        var nextCommunityId = cursorDtos.stream()
                .mapToLong(CursorDto::getCommunityId)
                .min()
                .orElse(CursorRequest.NONE_communityId);
        return new CursorResponse<>(cursorRequest.next(nextCommunityId), cursorDtos);
    }

    private List<CursorDto> selectAllPageByAccountId(Long accountId, CursorRequest cursorRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        map.put("size", cursorRequest.getSize());

        List<CursorDto> cursorDto;
        if (cursorRequest.hasCommunityId()) {
            map.put("communityId", cursorRequest.getCommunityId());
            cursorDto = communityReadMapper.selectNextPage(map);
        } else {
            cursorDto = communityReadMapper.selectFirstPage(map);
        }
        return cursorDto;
    }


    @Override
    public void updateCommunityImageEnabled(CommunityImageEnabledDto communityImageEnabledDto) {
        communityWriteMapper.updateCommunityImageEnabled(communityImageEnabledDto);
    }










    private void addTag(String tags, Integer communityId) {
        List<String> tagList = null;
        try {
            tagList = objectMapper.readValue(tags, ArrayList.class);
        } catch (JsonProcessingException e) {
            log.error("Tag parsing ERROR: {}", e.getMessage());
        }

        if (tagList != null) {
            for (String tag : tagList) {
                int tagId = tagService.selectTagId(tag);
                insertTag(communityId, tagId);
            }
        }
    }

    private void insertTag(Integer communityId, int tagId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("tagId", tagId);
        map.put("communityId",communityId);
        communityWriteMapper.insertCommunityTag(map);
    }



    private void updateArticle(ArticleModificationFormDto articleModificationFormDto) {
        ArticleModificationDto articleModificationDto = ArticleModificationDto.updateArticleModificationDto(articleModificationFormDto);
        communityWriteMapper.updateArticle(articleModificationDto);
    }

    private CommunityNewDto createCommunity(CommunityNewReqDto communityNewReqDto, Long accountId) {
        CommunityNewDto communityNewDto = CommunityNewDto.createNewCommunity(communityNewReqDto, accountId);
        communityWriteMapper.insertCommunity(communityNewDto);
        return communityNewDto;
    }

    private void deleteArticle(Long communityId, Long accountId) {
        Map<String, Long> map = new HashMap<>();
        map.put("communityId", communityId);
        map.put("accountId", accountId);
        communityWriteMapper.deleteArticle(map);
    }

    private ArticleModificationFormDto loadArticleData(Long communityId, UserAccount userAccount) {
        ArticleModificationFormDto articleModificationFormDto = communityReadMapper.selectArticleUpdateByCommunityId(communityId);
        if (!articleModificationFormDto.getAccountId().equals(userAccount.accountId())) {
            throw new UnauthorizedAccessException("잘못된 접근입니다.");
        }
        return articleModificationFormDto;
    }
}
