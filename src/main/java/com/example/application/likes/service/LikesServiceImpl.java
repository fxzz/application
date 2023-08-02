package com.example.application.likes.service;

import com.example.application.community.mapper.CommunityWriteMapper;
import com.example.application.likes.dto.LikesDto;
import com.example.application.likes.dto.UpDownDto;
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
    private final CommunityWriteMapper communityWriteMapper;





    @Override
    public Long saveAndLike(LikesDto likesDto, UserAccount userAccount) {
        Long likesCount = likesReadMapper.selectLastLikesCount(likesDto.getCommunityId());
        if (likesCount == null) {
            likesCount = 0L;
        }
        likesDto.setLikesCount(likesCount);
        likesWriteMapper.insertLikes(likesDto);



        UpDownDto upDownDto = new UpDownDto(likesDto.getCommunityId(), likesDto.getAccountId() , likesDto.getChange());
        likesWriteMapper.upDownLikes(upDownDto);

        HashMap<String, Long> map = new HashMap<>();
        map.put("communityId", likesDto.getCommunityId());
        map.put("accountId", likesDto.getAccountId());

        Long likes = likesReadMapper.SelectLikes(map);

        HashMap<String, Long> communityUpdateLikes = new HashMap<>();
        communityUpdateLikes.put("communityId", likesDto.getCommunityId());
        communityUpdateLikes.put("likes", likes);

        communityWriteMapper.updateLikes(communityUpdateLikes);


        return likes;

    }


}
