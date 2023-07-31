package com.example.application.comment.service;

import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;
import com.example.application.comment.dto.SelectCommentDto;
import com.example.application.comment.mapper.CommentReadMapper;
import com.example.application.comment.mapper.CommentWriteMapper;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentWriteMapper commentWriteMapper;
    private final CommentReadMapper commentReadMapper;



    @Override
    public void createComment(InsertCommentDto insertCommentDto, UserAccount userAccount) {
        insertCommentDto.setAccountId(userAccount.accountId());
        commentWriteMapper.insertComment(insertCommentDto);
    }


    @Override
    public List<SelectCommentDto> readComment(Long communityId) {
        return commentReadMapper.selectComment(communityId);
    }

    @Override
    public void createReplayComment(InsertReplyDto insertReplyDto, UserAccount userAccount) {
        insertReplyDto.setAccountId(userAccount.accountId());
        commentWriteMapper.insertReply(insertReplyDto);
    }

}
