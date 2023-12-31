package com.example.application.account.controller;


import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.account.dto.NotificationUpdateDto;
import com.example.application.account.service.AccountService;
import com.example.application.account.validator.AccountPasswordValidator;
import com.example.application.account.validator.AccountProfileValidator;
import com.example.application.account.validator.AccountSignUpValidator;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountPasswordValidator accountPasswordValidator;
    private final AccountProfileValidator accountProfileValidator;
    private final AccountSignUpValidator accountSignUpValidator;
    private final AccountService accountService;

    @Value("${recaptchaSiteKey}")
    private String recaptchaSiteKey;

    @InitBinder("passwordChangeReqDto")
    public void passwordInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountPasswordValidator);
    }

    @InitBinder("accountProfileRespDto")
    public void profileInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountProfileValidator);
    }

    @InitBinder("accountSignUpReqDto")
    public void signUpInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountSignUpValidator);
    }


    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String captcha, Model model) {
        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
        if ("true".equals(captcha)) {
            model.addAttribute("showCaptcha", true);
        }
        return "account/login";
    }

    @GetMapping("/signup")
    public String signUpForm(AccountSignUpReqDto accountSignUpReqDto, Model model) {
        model.addAttribute(accountSignUpReqDto);
        return "account/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid AccountSignUpReqDto accountSignUpReqDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(accountSignUpReqDto);
            return "account/signup";
        }
        accountService.saveAccount(accountSignUpReqDto);
        return "redirect:/";
    }

    @GetMapping("/profile/setting")
    public String profileForm(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        AccountProfileRespDto accountProfileRespDto = accountService.getUserProfileByAccountId(userAccount.accountId());
        model.addAttribute("accountProfileRespDto", accountProfileRespDto);
        model.addAttribute("userAccount", userAccount.getAccount());
        return "account/profile";
    }

    @PostMapping("/profile/setting")
    public String profile(@Valid AccountProfileRespDto accountProfileRespDto, BindingResult bindingResult, @AuthenticationPrincipal UserAccount userAccount, Model model, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("accountProfileRespDto", accountProfileRespDto);
            return "account/profile";
        }
        accountService.saveProfile(accountProfileRespDto, userAccount.accountId());
        attributes.addFlashAttribute("msg", "설정을 저장했습니다.");
        return "redirect:/profile/setting";
    }

    @GetMapping("/profile/password")
    public String passwordForm(Model model) {
        model.addAttribute(new PasswordChangeReqDto());
        return "account/password";
    }

    @PostMapping("/profile/password")
    public String password(@Valid PasswordChangeReqDto passwordChangeReqDto, BindingResult bindingResult, @AuthenticationPrincipal UserAccount userAccount, Model model, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("passwordChangeReqDto", passwordChangeReqDto);
            return "account/password";
        }
        accountService.passwordChange(userAccount.getAccount(), passwordChangeReqDto);
        attributes.addFlashAttribute("msg", "패스워드를 변경했습니다.");
        return "redirect:/profile/password";
    }

    @GetMapping("/profile/notification")
    public String getNotification(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        boolean notificationEnabled = accountService.selectNotificationEnabled(userAccount.accountId());
        model.addAttribute("notificationEnabled", notificationEnabled);
        log.info("notificationEnabled {}", notificationEnabled);
        return "account/notification";
    }


    @PostMapping("/profile/notification")
    public String updateNotification(NotificationUpdateDto notificationUpdateDto, @AuthenticationPrincipal UserAccount userAccount, RedirectAttributes attributes) {
        notificationUpdateDto.setAccountId(userAccount.accountId());

        accountService.updateNotificationEnabled(notificationUpdateDto);
        attributes.addFlashAttribute("msg", "알림 설정을 업데이트 했습니다");
        return "redirect:/profile/notification";
    }

}
