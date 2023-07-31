package com.example.application.comment.mapper;

import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;
import com.example.application.comment.dto.SelectCommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
   void insertComment(InsertCommentDto insertCommentDto);
   void insertReply(InsertReplyDto insertReplyDto);
   List<SelectCommentDto> selectComment(Long communityId);
}
