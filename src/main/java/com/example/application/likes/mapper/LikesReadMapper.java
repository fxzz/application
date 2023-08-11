package com.example.application.likes.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LikesReadMapper {
   Long selectLikesCount(Long communityId);


}
