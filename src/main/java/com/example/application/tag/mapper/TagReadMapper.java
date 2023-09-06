package com.example.application.tag.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagReadMapper {

   Integer selectTitleCount(String tagTitle);
   List<String> selectAllTag();
   int selectTagId(String tagTitle);
}
