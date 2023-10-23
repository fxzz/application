package com.example.application.comment.service;

import com.example.application.comment.dto.DeleteCommentDto;
import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;
import com.example.application.comment.dto.SelectCommentDto;
import com.example.application.comment.event.CommentCreatedEvent;
import com.example.application.comment.mapper.CommentReadMapper;
import com.example.application.comment.mapper.CommentWriteMapper;
import com.example.application.community.mapper.CommunityWriteMapper;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentWriteMapper commentWriteMapper;
    private final CommentReadMapper commentReadMapper;
    private final CommunityWriteMapper communityWriteMapper;
    private final ApplicationEventPublisher eventPublisher;



    @Override
    public void createComment(InsertCommentDto insertCommentDto, UserAccount userAccount) {
        saveComment(insertCommentDto, userAccount);
        eventPublisher.publishEvent(new CommentCreatedEvent(insertCommentDto));
    }



    @Transactional(readOnly = true)
    @Override
    public List<SelectCommentDto> readComment(Long communityId) {
        return commentReadMapper.selectComment(communityId);
    }

    @Override
    public void createReplayComment(InsertReplyDto insertReplyDto, UserAccount userAccount) {
        saveReplayComment(insertReplyDto, userAccount);
    }


    @Override
    public void removeComment(DeleteCommentDto deleteCommentDto) {
        commentWriteMapper.deleteComment(deleteCommentDto);
    }



    private void saveComment(InsertCommentDto insertCommentDto, UserAccount userAccount) {
        insertCommentDto.setAccountId(userAccount.accountId());
        commentWriteMapper.insertComment(insertCommentDto);
        communityWriteMapper.updateCommentCnt(insertCommentDto.getCommunityId());
    }

    private void saveReplayComment(InsertReplyDto insertReplyDto, UserAccount userAccount) {
        insertReplyDto.setAccountId(userAccount.accountId());
        commentWriteMapper.insertReply(insertReplyDto);
        communityWriteMapper.updateCommentCnt(insertReplyDto.getCommunityId());
    }
}
