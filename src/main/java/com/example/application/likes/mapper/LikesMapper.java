package com.example.application.likes.mapper;

import com.example.application.likes.dto.LikesDto;
import com.example.application.likes.dto.UpDownDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LikesMapper {
   void insertLikes(LikesDto likesDto);
   void upDownLikes(UpDownDto upDownDto);
   Long SelectLikes(Map map);
   Long selectLastLikesCount(Long communityId);


}
