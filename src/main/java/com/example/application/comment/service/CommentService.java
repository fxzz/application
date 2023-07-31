package com.example.application.comment.service;

import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;
import com.example.application.comment.dto.SelectCommentDto;
import com.example.application.security.UserAccount;

import java.util.List;

public interface CommentService {
    void insertComment(InsertCommentDto insertCommentDto);
    void insertReply(InsertReplyDto insertReplyDto);

    void createComment(InsertCommentDto insertCommentDto, UserAccount userAccount);
    List<SelectCommentDto> selectComment(Long communityId);

    List<SelectCommentDto> readComment(Long communityId);

    void createReplayComment(InsertReplyDto insertReplyDto, UserAccount userAccount);
}
