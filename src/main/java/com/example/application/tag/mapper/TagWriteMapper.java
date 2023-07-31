package com.example.application.tag.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagWriteMapper {

    void insertTag(String tagTitle);
}
