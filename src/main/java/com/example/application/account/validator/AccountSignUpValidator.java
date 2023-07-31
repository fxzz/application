package com.example.application.account.validator;

import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountSignUpValidator implements Validator {

    private final AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountSignUpReqDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountSignUpReqDto accountSignUpReqDto = (AccountSignUpReqDto) target;

        if (accountService.selectUsernameCount(accountSignUpReqDto.getUsername()) != 0) {
            errors.rejectValue("username", "username", "사용중인 아이디 입니다.");
        }

        if (accountService.selectEmailCount(accountSignUpReqDto.getEmail()) != 0) {
            errors.rejectValue("email", "email", "사용중인 이메일 입니다.");
        }

        if (accountService.selectNicknameCount(accountSignUpReqDto.getNickname()) != 0) {
            errors.rejectValue("nickname", "nickname", "사용중인 닉네임 입니다.");
        }


    }
}


