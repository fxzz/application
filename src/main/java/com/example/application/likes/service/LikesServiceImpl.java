package com.example.application.likes.service;

import com.example.application.likes.dto.LikesDto;
import com.example.application.likes.dto.UpDownDto;
import com.example.application.likes.mapper.LikesMapper;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesMapper likesMapper;


    @Override
    public void insertLikes(LikesDto likesDto) {
        likesMapper.insertLikes(likesDto);
    }

    @Override
    public void upDownLikes(UpDownDto upDownDto) {
        likesMapper.upDownLikes(upDownDto);
    }

    @Override
    public Long saveAndLike(LikesDto likesDto, UserAccount userAccount) {
        Long likesCount = selectLastLikesCount(likesDto.getCommunityId());
        if (likesCount == null) {
            likesCount = 0L;
        }

        likesDto.setLikesCount(likesCount);
        insertLikes(likesDto);


        UpDownDto upDownDto = new UpDownDto(likesDto.getCommunityId(), likesDto.getAccountId() , likesDto.getChange());
        upDownLikes(upDownDto);

        HashMap<String, Long> map = new HashMap<>();
        map.put("communityId", likesDto.getCommunityId());
        map.put("accountId", likesDto.getAccountId());


        return SelectLikes(map);

    }

    @Override
    public Long SelectLikes(Map map) {
        return likesMapper.SelectLikes(map);
    }

    @Override
    public Long selectLastLikesCount(Long communityId) {
        return likesMapper.selectLastLikesCount(communityId);
    }


}
