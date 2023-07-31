package com.example.application.comment.mapper;

import com.example.application.comment.dto.SelectCommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentReadMapper {

    List<SelectCommentDto> selectComment(Long communityId);
}
