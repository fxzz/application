package com.example.application.community.service;

import com.example.application.account.mapper.AccountReadMapper;
import com.example.application.community.dto.*;
import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.mapper.CommunityReadMapper;
import com.example.application.community.mapper.CommunityWriteMapper;
import com.example.application.image.service.ImageService;
import com.example.application.security.UserAccount;
import com.example.application.tag.service.TagService;
import com.example.application.util.exception.InvalidAccessOperationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final ImageService imageService;
    private final AccountReadMapper accountReadMapper;

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
    public void saveCommunity(CommunityNewReqDto communityNewReqDto, Long accountId, MultipartFile[] files, String uploadPath) throws IOException {
        CommunityNewDto communityNewDto = getCommunity(communityNewReqDto, accountId);

        communityWriteMapper.insertCommunity(communityNewDto);


        if (files != null) {
            imageService.uploadFile(files, communityNewDto.getCommunityId().longValue(), uploadPath);
        }

            List<String> tagList = objectMapper.readValue(communityNewReqDto.getTag(), ArrayList.class);
            for (String tag : tagList) {
              int tagId = tagService.selectTagId(tag);
              Map<String, Integer> map = new HashMap<>();
              map.put("tagId", tagId);
              map.put("communityId",communityNewDto.getCommunityId());
                communityWriteMapper.insertCommunityTag(map);
                log.info("communityId : {}", communityNewDto.getCommunityId());
            }
    }



    @Transactional(readOnly = true)
    @Override
    public List<CommunityTagResultDto> CommunityTagPagedLimitByKeyword(SearchCondition searchCondition) {

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
        return communityReadMapper.selectCommunityByCommunityId(communityId);
    }



    @Override
    public void deleteArticleAction(Long communityId, Long accountId) {
        Map<String, Long> map = new HashMap<>();
        map.put("communityId", communityId);
        map.put("accountId", accountId);
        communityWriteMapper.deleteCommunityTagsByCommunityId(communityId);
        communityWriteMapper.deleteArticle(map);
    }


    @Override
    public void updateArticleView(Long communityId) {
        communityWriteMapper.updateArticleView(communityId);
    }





    @Transactional(readOnly = true)
    @Override
    public ArticleModificationFormDto getArticleModificationForm(Long communityId, UserAccount userAccount) {
        ArticleModificationFormDto articleModificationFormDto = communityReadMapper.selectArticleUpdateByCommunityId(communityId);
        if (!articleModificationFormDto.getAccountId().equals(userAccount.accountId())) {
            throw new InvalidAccessOperationException("잘못된 접근입니다.");
        }

        return articleModificationFormDto;
    }

    @Override
    public void modifyArticle(Integer communityId, ArticleModificationFormDto articleModificationFormDto, MultipartFile[] files, String uploadPath) throws IOException {
        ArticleModificationDto articleModificationDto = getArticleModificationDto(articleModificationFormDto);

        communityWriteMapper.deleteCommunityTagsByCommunityId(articleModificationFormDto.getCommunityId());
        updateArticle(articleModificationDto);

        if (files != null) {
            imageService.uploadFile(files, communityId.longValue(), uploadPath);
        }

            List<String> tagList = objectMapper.readValue(articleModificationFormDto.getTag(), ArrayList.class);
            for (String tag : tagList) {
                int tagId = tagService.selectTagId(tag);
                Map<String, Integer> map = new HashMap<>();
                map.put("tagId", tagId);
                map.put("communityId",communityId);
                communityWriteMapper.insertCommunityTag(map);
            }


    }



    @Override
    public void updateArticle(ArticleModificationDto articleModificationDto) {
        communityWriteMapper.updateArticle(articleModificationDto);
    }

    @Override
    public void updateCommunityImageEnabled(CommunityImageEnabledDto communityImageEnabledDto) {
        communityWriteMapper.updateCommunityImageEnabled(communityImageEnabledDto);
    }

    private CommunityNewDto getCommunity(CommunityNewReqDto communityNewReqDto, Long accountId) {
        CommunityNewDto communityNewDto = CommunityNewDto.builder()
                .title(communityNewReqDto.getTitle())
                .content(communityNewReqDto.getContent())
                .accountId(accountId)
                .build();
        return communityNewDto;
    }

    private ArticleModificationDto getArticleModificationDto(ArticleModificationFormDto articleModificationFormDto) {
        ArticleModificationDto articleModificationDto = ArticleModificationDto.builder()
                                       .communityId(articleModificationFormDto.getCommunityId())
                                       .title(articleModificationFormDto.getTitle())
                                       .content(articleModificationFormDto.getContent())
                                       .modifiedAt(LocalDateTime.now())
                                       .build();
        return articleModificationDto;
    }
}
