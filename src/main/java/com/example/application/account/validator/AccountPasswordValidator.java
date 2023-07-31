package com.example.application.account.validator;

import com.example.application.account.dto.AccountReqDto.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountPasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordChangeReqDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
      PasswordChangeReqDto passwordChangeReqDto = (PasswordChangeReqDto) target;
        if (!passwordChangeReqDto.getNewPassword().equals(passwordChangeReqDto.getNewPasswordConfirm())) {
            errors.rejectValue("newPassword", "newPassword", "신규 패스워드가 일치하지 않습니다.");
        }
    }
}
