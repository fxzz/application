package com.example.application.account.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;


public class AccountRespDto {

    @ToString
    @Setter
    @Getter
    public static class AccountProfileRespDto {

        @Pattern(regexp = "^[가-힣A-Za-z]{1,20}$", message = "한글 또는 영어 이름을 입력해주세요.")
        private String fullName;

        @Pattern(regexp = "^[가-힣a-z0-9]{1,20}$", message = "20자 이하의 알파벳, 한글, 숫자 이어야 합니다.")
        private String nickname;



    }


}
