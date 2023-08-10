package com.example.application.likes.controller;

import com.example.application.likes.dto.LikesDto;
import com.example.application.likes.service.LikesService;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LikesController {

    private final LikesService likesService;


    @ResponseBody
    @PostMapping("/likes")
    public ResponseEntity insertLikes(@RequestBody LikesDto likesDto, @AuthenticationPrincipal UserAccount userAccount) {
        likesService.insertLikes(likesDto, userAccount);
        return ResponseEntity.ok().build();
    }


}
