package com.example.application.comment.mapper;

import com.example.application.comment.dto.DeleteCommentDto;
import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CommentWriteMapper {
   void insertComment(InsertCommentDto insertCommentDto);
   void insertReply(InsertReplyDto insertReplyDto);
   void deleteComment(DeleteCommentDto deleteCommentDto);

}
