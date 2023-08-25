package com.example.application.comment.controller;

import com.example.application.comment.dto.DeleteCommentDto;
import com.example.application.comment.dto.InsertCommentDto;
import com.example.application.comment.dto.InsertReplyDto;
import com.example.application.comment.dto.SelectCommentDto;
import com.example.application.comment.service.CommentService;
import com.example.application.security.UserAccount;
import com.example.application.util.ErrorCode;
import com.example.application.util.ResponseDto;
import com.example.application.util.ValidationMessageUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;





    @PostMapping("/comment/new")
    public ResponseEntity<?> commentNew(@RequestBody @Valid InsertCommentDto insertCommentDto, BindingResult bindingResult, @AuthenticationPrincipal UserAccount userAccount) {
        if (bindingResult.hasErrors()) {
            String errorMessage = ValidationMessageUtils.extractErrorMessage(bindingResult,"content");
            return new ResponseEntity<>(new ResponseDto<>(ErrorCode.FAILURE, errorMessage, null), HttpStatus.BAD_REQUEST);
        }
        commentService.createComment(insertCommentDto, userAccount);
        return new ResponseEntity<>(new ResponseDto<>(ErrorCode.SUCCESS, CommentConstants.COMMENT_CREATED_MESSAGE, null), HttpStatus.CREATED);
    }

    @GetMapping("/comment/{communityId}/read")
    public ResponseEntity<ResponseDto<List<SelectCommentDto>>> commentRead(@PathVariable Long communityId) {
        List<SelectCommentDto> selectCommentDto = commentService.readComment(communityId);
        log.info("selectCommentDto {}", selectCommentDto);
        return new ResponseEntity<>(new ResponseDto<>(ErrorCode.SUCCESS, CommentConstants.EMPTY_STRING, selectCommentDto), HttpStatus.OK);
    }

    @PostMapping("/comment/reply")
    public  ResponseEntity<?> commentReply(@RequestBody @Valid InsertReplyDto insertReplyDto, BindingResult bindingResult, @AuthenticationPrincipal UserAccount userAccount) {
        if (bindingResult.hasErrors()) {
            String errorMessage = ValidationMessageUtils.extractErrorMessage(bindingResult,"content");
            return new ResponseEntity<>(new ResponseDto<>(ErrorCode.FAILURE, errorMessage, null), HttpStatus.BAD_REQUEST);
        }
        commentService.createReplayComment(insertReplyDto, userAccount);
        return new ResponseEntity<>(new ResponseDto<>(ErrorCode.SUCCESS, CommentConstants.REPLY_CREATED_MESSAGE, null), HttpStatus.CREATED);
    }

    @PostMapping("/comment/{commentId}/remove")
    public ResponseEntity<?> commentRemove(@PathVariable Long commentId, @AuthenticationPrincipal UserAccount userAccount) {
        commentService.removeComment(new DeleteCommentDto(commentId, userAccount.getAccount().getAccountId()));
        return new ResponseEntity<>(new ResponseDto<>(ErrorCode.SUCCESS, CommentConstants.COMMENT_DELETED_MESSAGE, null), HttpStatus.OK);
    }
}
