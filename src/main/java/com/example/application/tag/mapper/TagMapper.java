package com.example.application.tag.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
   void insertTag(String tagTitle);
   int selectTitleCount(String tagTitle);
   List<String> selectAllTag();
   int selectTagId(String tagTitle);
}
