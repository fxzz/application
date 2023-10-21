package com.example.application.community.controller;

import com.example.application.community.dto.*;
import com.example.application.community.dto.CommunityDto.*;
import com.example.application.community.service.CommunityService;
import com.example.application.community.service.RankingService;
import com.example.application.community.service.RankingServiceImpl;
import com.example.application.security.UserAccount;
import com.example.application.tag.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final TagService tagService;
    private final ObjectMapper objectMapper;
    private final RankingService rankingService;

    @Value("${uploadPath}")
    private String uploadPath;

    @GetMapping("/user/{nickname}/activity")
    public String getUserActivity(@PathVariable String nickname, Model model) {
        model.addAttribute("nickname", nickname);
        return "community/activity";
    }

    @GetMapping("/user/{nickname}/activityData")
    @ResponseBody
    public CursorResponse<CursorDto> getUserActivity(@PathVariable String nickname, @RequestParam(required = false) Long communityId, int size){
        return communityService.getCursorPage(nickname, new CursorRequest(communityId, size));
    }

    //todo 모든 겟페이지에 모델로 로그인 보내서 프사 고정

    @GetMapping("/")
    public String main() {
        return "redirect:/community";
    }

    @GetMapping("/community")
    public String community(Model model, SearchCondition searchCondition, @AuthenticationPrincipal UserAccount userAccount) {
        List<CommunityTagResultDto> communityTagResultDto  = communityService.getCommunityAll(searchCondition);
        PageHandler ph = communityService.createPageHandler(searchCondition);
        model.addAttribute("communityTagResultDto", communityTagResultDto);
        model.addAttribute("ph", ph);

        List<RankIngLikesDto> rankIngLikesDtoList = rankingService.getTopLikesRank(10);
        model.addAttribute("rankIngLikesDtoList", rankIngLikesDtoList);
        rankingService.setRankIngLikesDtoList(rankIngLikesDtoList);
        return "community/community";
    }

    @GetMapping("/community/new")
    public String communityNewForm(Model model) throws JsonProcessingException {
        model.addAttribute("whitelist", objectMapper.writeValueAsString(tagService.selectAllTag()));
        return "community/new";
    }

    @PostMapping("/community/new")
    public String communityNew(@Valid CommunityNewReqDto communityNewReqDto, BindingResult bindingResult, MultipartFile[] files, @AuthenticationPrincipal UserAccount userAccount, Model model, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titleError", bindingResult.getFieldError("title") != null ? bindingResult.getFieldError("title").getDefaultMessage() : null);
            model.addAttribute("contentError", bindingResult.getFieldError("content") != null ? bindingResult.getFieldError("content").getDefaultMessage() : null);
            model.addAttribute("communityNewReqDto", communityNewReqDto);
            return "community/new";
        }
        communityService.saveCommunity(communityNewReqDto, userAccount.accountId(), files, uploadPath);
        attributes.addFlashAttribute("msg", "글쓰기를 성공 했습니다.");
        return "redirect:/community";
    }


    @GetMapping("article/{communityId}")
    public String article(@PathVariable Long communityId, Model model, @AuthenticationPrincipal UserAccount userAccount) {
        ArticleDto articleDto = communityService.getArticleById(communityId);
        communityService.updateArticleView(communityId);
        model.addAttribute("articleDto", articleDto);
        model.addAttribute("account", userAccount.getAccount());


        List<RankIngLikesDto> rankIngLikesDtoList = rankingService.getRankIngLikesDtoList();
        model.addAttribute("rankIngLikesDtoList", rankIngLikesDtoList);

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
        ArticleModificationFormDto articleModificationFormDto = communityService.getArticleModifyForm(communityId, userAccount);
        model.addAttribute("articleModificationFormDto", articleModificationFormDto);
        model.addAttribute("whitelist", objectMapper.writeValueAsString(tagService.selectAllTag()));
        return "community/articleEdit";
    }

    @PostMapping("/articles/{communityId}/modify")
    public String articleModify(@PathVariable Integer communityId, MultipartFile[] files, @Valid ArticleModificationFormDto articleModificationFormDto, BindingResult bindingResult, Model model, RedirectAttributes attributes) throws JsonProcessingException {
       if (bindingResult.hasErrors()) {
           model.addAttribute("articleModificationFormDto", articleModificationFormDto);
           model.addAttribute("whitelist", objectMapper.writeValueAsString(tagService.selectAllTag()));
           return "community/articleEdit";
       }

       communityService.modifyArticle(communityId, articleModificationFormDto, files, uploadPath);
       attributes.addFlashAttribute("msg", "글을 변경 했습니다.");


        return "redirect:/community";
    }


    @PostMapping("/imageEnabled")
    @ResponseBody
    public ResponseEntity<String> communityImageEnabled(@RequestBody CommunityImageEnabledDto communityImageEnabledDto) {
        communityService.updateCommunityImageEnabled(communityImageEnabledDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
