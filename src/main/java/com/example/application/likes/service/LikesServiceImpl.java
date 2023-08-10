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


@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class LikesServiceImpl implements LikesService {

    private final LikesReadMapper likesReadMapper;
    private final LikesWriteMapper likesWriteMapper;






    @Override
    public void insertLikes(LikesDto likesDto, UserAccount userAccount) {
        likesDto.setAccountId(userAccount.accountId());
        likesWriteMapper.insertLikes(likesDto);

    }

    @Transactional(readOnly = true)
    @Override
    public Long selectLikesCount(Long communityId) {
        return likesReadMapper.selectLikesCount(communityId);
    }


}
