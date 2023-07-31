package com.example.application.comment.service;

import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;
import com.example.application.comment.dto.SelectCommentDto;
import com.example.application.comment.mapper.CommentMapper;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void insertComment(InsertCommentDto insertCommentDto) {
        commentMapper.insertComment(insertCommentDto);
    }

    @Override
    public void insertReply(InsertReplyDto insertReplyDto) {
        commentMapper.insertReply(insertReplyDto);
    }

    @Override
    public void createComment(InsertCommentDto insertCommentDto, UserAccount userAccount) {
        insertCommentDto.setAccountId(userAccount.accountId());
        insertComment(insertCommentDto);
    }

    @Override
    public List<SelectCommentDto> selectComment(Long communityId) {
        return commentMapper.selectComment(communityId);
    }

    @Override
    public List<SelectCommentDto> readComment(Long communityId) {
        return selectComment(communityId);
    }

    @Override
    public void createReplayComment(InsertReplyDto insertReplyDto, UserAccount userAccount) {
        insertReplyDto.setAccountId(userAccount.accountId());
        insertReply(insertReplyDto);
    }

}
