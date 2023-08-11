package com.example.application.likes.mapper;

import com.example.application.likes.dto.LikesDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikesWriteMapper {
    void insertLikes(LikesDto likesDto);
}
