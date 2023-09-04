package com.example.application.likes.mapper;


import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface LikesReadMapper {
   Long selectLikesCount(Long communityId);



}
