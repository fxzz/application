package com.example.application.account;


import com.example.application.WithAccount;
import com.example.application.account.dto.Account;
import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.mapper.AccountReadMapper;

import com.example.application.account.mapper.AccountWriteMapper;
import com.example.application.account.service.AccountService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountReadMapper accountReadMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;





    @DisplayName("회원 가입 화면 보이는지 테스트")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/signup"))
                .andExpect(model().attributeExists("accountSignUpReqDto"));
    }

    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    void signUpSubmit_with_wrong_input() throws Exception {
        mockMvc.perform(post("/signup")
                .param("username", "yyy")
                .param("password", "1")
                .param("email", "email..")
                .param("fullName", "yyy")
                .param("nickname", "yyy")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/signup"));
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    void signUpSubmit_with_correct_input() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "yyyyy")
                        .param("password", "12345")
                        .param("email", "email@gmail.com")
                        .param("fullName", "yyyyy")
                        .param("nickname", "yyyyy")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));


        Boolean emailExists = accountReadMapper.selectEmailCount("email@gmail.com") > 0;
        assertTrue(emailExists);

    }

    @WithAccount("ddddd")
    @DisplayName("로그인 성공")
    @Test
    void login_success() throws Exception {

        mockMvc.perform(post("/login")
                .param("username", "ddddd")
                .param("password", "12345")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }


    @DisplayName("로그인 실패")
    @Test
    void login_fail() throws Exception {

        mockMvc.perform(post("/login")
                        .param("username", "ddd")
                        .param("password", "123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));

    }

    @WithMockUser
    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/logout")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @WithAccount("fffffff")
    @DisplayName("프로필 수정 폼")
    @Test
    void updateProfileForm() throws Exception {

        mockMvc.perform(get("/profile/setting"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accountProfileRespDto"))
                .andExpect(model().attributeExists("userAccount"))
                .andExpect(view().name("account/profile"));

    }

    @WithAccount("fffff")
    @DisplayName("프로필 수정하기 - 입력값 정상")
    @Test
    void updateProfile() throws Exception {

        mockMvc.perform(post("/profile/setting")
                        .param("fullName", "ggggg")
                        .param("nickname", "ggggg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/setting"));

              int nicknameCount = accountReadMapper.selectNicknameCount("ggggg");
              assertTrue(nicknameCount == 1);

    }

    @WithAccount("wwwww")
    @DisplayName("프로필 수정하기 - 입력값 에러")
    @Test
    void updateProfile_error() throws Exception {

        mockMvc.perform(post("/profile/setting")
                        .param("fullName", "12345678910 12345678910")
                        .param("nickname", "ggggggg")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/profile"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("accountProfileRespDto"));

        int nicknameCount = accountReadMapper.selectNicknameCount("wwwww");
        assertTrue(nicknameCount == 1);
    }

    @WithAccount("dddddddd")
    @DisplayName("패스워드 수정 폼")
    @Test
    void updatePassword_form() throws Exception {
        mockMvc.perform(get("/profile/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/password"));
    }


    @WithAccount("sssss")
    @DisplayName("패스워드 수정 - 입력값 정상")
    @Test
    void updatePassword_success() throws Exception {
        mockMvc.perform(post("/profile/password")
                .param("password", "12345")
                .param("newPassword", "123456")
                .param("newPasswordConfirm", "123456")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/password"));

        Account account = accountReadMapper.selectUserByUsername("sssss");
        assertTrue(passwordEncoder.matches("123456", account.getPassword()));

    }

    @WithAccount("sssssss")
    @DisplayName("패스워드 수정 - 입력값 에러 - 패스워드 불일치")
    @Test
    void updatePassword_fail() throws Exception {
        mockMvc.perform(post("/profile/password")
                        .param("password", "12345")
                        .param("newPassword", "1234567")
                        .param("newPasswordConfirm", "1234568")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("passwordChangeReqDto"))
                .andExpect(view().name("account/password"));

        Account account = accountReadMapper.selectUserByUsername("sssssss");
        assertTrue(passwordEncoder.matches("12345", account.getPassword()));

    }



}
