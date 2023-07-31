package com.example.application.community.controller;

import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.dto.PageHandler;
import com.example.application.community.dto.SearchCondition;
import com.example.application.community.service.CommunityService;
import com.example.application.security.UserAccount;
import com.example.application.tag.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final TagService tagService;
    private final ObjectMapper objectMapper;



    //todo 모든 겟페이지에 모델로 로그인 보내서 프사 고정

    @GetMapping("/community")
    public String community(Model model, SearchCondition searchCondition, @AuthenticationPrincipal UserAccount userAccount) {
        List<CommunityTagResultDto> communityTagResultDto  = communityService.CommunityTagPagedLimitByKeyword(searchCondition);
        PageHandler ph = communityService.createPageHandler(searchCondition);
        model.addAttribute("communityTagResultDto", communityTagResultDto);
        model.addAttribute("ph", ph);
        log.debug("communityTagResultDto : {}", communityTagResultDto);
        return "community/community";
    }

    @GetMapping("/community/new")
    public String communityNewForm(Model model) throws JsonProcessingException {
        model.addAttribute("whitelist", objectMapper.writeValueAsString(tagService.selectAllTag()));
        return "community/new";
    }

    @PostMapping("/community/new")
    public String communityNew(@Valid CommunityNewReqDto communityNewReqDto,BindingResult bindingResult ,@AuthenticationPrincipal UserAccount userAccount, Model model, RedirectAttributes attributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titleError", bindingResult.getFieldError("title") != null ? bindingResult.getFieldError("title").getDefaultMessage() : null);
            model.addAttribute("contentError", bindingResult.getFieldError("content") != null ? bindingResult.getFieldError("content").getDefaultMessage() : null);
            model.addAttribute("communityNewReqDto", communityNewReqDto);
            return "community/new";
        }
        communityService.saveCommunity(communityNewReqDto, userAccount.accountId());
        attributes.addFlashAttribute("msg", "글쓰기를 성공 했습니다.");
        return "redirect:/community";
    }


    @GetMapping("article/{communityId}")
    public String article(@PathVariable Long communityId, Model model, @AuthenticationPrincipal UserAccount userAccount) {
              ArticleDto articleDto = communityService.getArticleById(communityId);
              model.addAttribute("articleDto", articleDto);
              model.addAttribute("account", userAccount.getAccount());
          //    log.debug("articleDto : {}", articleDto);
        return "community/article";
    }

    @PostMapping("/articles/{communityId}/delete")
    public String articleDelete(@PathVariable Long communityId, @AuthenticationPrincipal UserAccount userAccount, RedirectAttributes attributes) {
            communityService.deleteArticleAction(communityId, userAccount.accountId());
            attributes.addFlashAttribute("msg", "글을 삭제 했습니다.");
            return "redirect:/community";
    }

    @GetMapping("/articles/{communityId}/modify")
    public String articleModifyForm(@PathVariable Long communityId, @AuthenticationPrincipal UserAccount userAccount, Model model) throws JsonProcessingException {
        ArticleModificationFormDto articleModificationFormDto = communityService.getArticleModificationForm(communityId, userAccount);
        model.addAttribute("articleModificationFormDto", articleModificationFormDto);
        model.addAttribute("whitelist", objectMapper.writeValueAsString(tagService.selectAllTag()));
        return "community/articleEdit";
    }

    @PostMapping("/articles/{communityId}/modify")
    public String articleModify(@PathVariable Integer communityId, @Valid ArticleModificationFormDto articleModificationFormDto, BindingResult bindingResult, Model model, RedirectAttributes attributes) throws JsonProcessingException {
       if (bindingResult.hasErrors()) {
           model.addAttribute("articleModificationFormDto", articleModificationFormDto);
           model.addAttribute("whitelist", objectMapper.writeValueAsString(tagService.selectAllTag()));
           return "community/articleEdit";
       }

       communityService.modifyArticle(communityId, articleModificationFormDto);
       attributes.addFlashAttribute("msg", "글을 변경 했습니다.");


        return "redirect:/community";
    }





}
