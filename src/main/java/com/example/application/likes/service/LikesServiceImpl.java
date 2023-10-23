package com.example.application.likes.service;

import com.example.application.community.mapper.CommunityWriteMapper;
import com.example.application.likes.dto.LikesDto;
import com.example.application.likes.mapper.LikesReadMapper;
import com.example.application.likes.mapper.LikesWriteMapper;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class LikesServiceImpl implements LikesService {

    private final LikesReadMapper likesReadMapper;
    private final LikesWriteMapper likesWriteMapper;
    private final CommunityWriteMapper communityWriteMapper;





    @Transactional
    @Override
    public void insertLikes(LikesDto likesDto, UserAccount userAccount) {
        likesValid(likesDto, userAccount);

        saveLikes(likesDto, userAccount);
    }




    private void likesValid(LikesDto likesDto, UserAccount userAccount) {
        LikesDto likes = likesReadMapper.selectUserExists(likesDto.getCommunityId(), userAccount.accountId());
        if (likes != null) {
            likes.likesValid();
        }
    }


    private void saveLikes(LikesDto likesDto, UserAccount userAccount) {
        likesDto.setAccountId(userAccount.accountId());
        likesWriteMapper.insertLikes(likesDto);
        updateCommunityLikesCount(likesDto);
    }

    private void updateCommunityLikesCount(LikesDto likesDto) {
        Long likesCount = likesReadMapper.selectLikesCount(likesDto.getCommunityId());
        Map map = new HashMap();
        map.put("communityId", likesDto.getCommunityId());
        map.put("likes", likesCount);
        communityWriteMapper.updateLikes(map);
    }


}
