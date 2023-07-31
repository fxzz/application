package com.example.application.account.validator;

import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AccountProfileValidator implements Validator {

    private final AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountProfileRespDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
      AccountProfileRespDto accountProfileRespDto = (AccountProfileRespDto) target;

        if (accountService.selectNicknameCount(accountProfileRespDto.getNickname()) != 0) {
            errors.rejectValue("nickname", "nickname", "사용중인 닉네임 입니다.");
        }

    }
}
