package com.example.application.likes.service;

import com.example.application.likes.dto.LikesDto;
import com.example.application.security.UserAccount;

public interface LikesService {

    void insertLikes(LikesDto likesDto, UserAccount userAccount);
    Long selectLikesCount(Long communityId);
}
