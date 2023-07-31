package com.example.application.comment.service;

import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;
import com.example.application.comment.dto.SelectCommentDto;
import com.example.application.security.UserAccount;

import java.util.List;

public interface CommentService {

    void createComment(InsertCommentDto insertCommentDto, UserAccount userAccount);
    List<SelectCommentDto> readComment(Long communityId);
    void createReplayComment(InsertReplyDto insertReplyDto, UserAccount userAccount);
}
