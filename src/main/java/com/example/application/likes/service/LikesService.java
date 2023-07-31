package com.example.application.likes.service;

import com.example.application.likes.dto.LikesDto;
import com.example.application.likes.dto.UpDownDto;
import com.example.application.security.UserAccount;

import java.util.Map;

public interface LikesService {

    Long saveAndLike(LikesDto likesDto, UserAccount userAccount);
}
