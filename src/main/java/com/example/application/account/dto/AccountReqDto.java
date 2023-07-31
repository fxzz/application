package com.example.application.account.dto;

import lombok.*;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;



public class AccountReqDto {



    @Getter
    @Builder
    public static class AccountSignUpReqDto {


        @Pattern(regexp = "^[A-Za-z0-9]{3,20}$", message = "알파벳, 숫자로만 3자 이상 20자 이하여야 합니다.")
        private String username;

        @Pattern(regexp = "^[A-Za-z0-9]{4,}$", message = "알파벳, 숫자로만 4자 이상이어야 합니다.")
        private String password;

        @Pattern(regexp = "^[A-Za-z0-9]{1,15}@[A-Za-z]{1,10}\\.[A-Za-z]{1,10}$", message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @Pattern(regexp = "^[가-힣A-Za-z]{1,20}$", message = "한글 또는 영어 이름을 입력해주세요.")
        private String fullName;

        @Pattern(regexp = "^[가-힣a-z0-9]{1,20}$", message = "20자 이하의 알파벳, 한글, 숫자 이어야 합니다.")
        private String nickname;



        public Account toAccount() {
            return  Account.builder()
                    .username(getUsername())
                    .password(getPassword())
                    .email(getEmail())
                    .fullName(getFullName())
                    .nickname(getNickname())
                    .role(Account.Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Setter
    @Getter
    public static class PasswordChangeReqDto {


        private String password;

        @Pattern(regexp = "^[A-Za-z0-9]{4,}$", message = "알파벳, 숫자로만 4자 이상이어야 합니다.")
        private String newPassword;

        @Pattern(regexp = "^[A-Za-z0-9]{4,}$", message = "알파벳, 숫자로만 4자 이상이어야 합니다.")
        private String newPasswordConfirm;
    }



}
