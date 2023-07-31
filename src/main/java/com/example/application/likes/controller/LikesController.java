package com.example.application.likes.controller;

import com.example.application.likes.dto.LikesDto;
import com.example.application.likes.service.LikesService;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/likes")
    public ResponseEntity addLikes(@RequestBody LikesDto likesDto, @AuthenticationPrincipal UserAccount userAccount) {
        Long likesCount = likesService.saveAndLike(likesDto, userAccount);
        log.debug("likesDto : {}", likesDto);
        return ResponseEntity.ok(likesCount);
    }
}
