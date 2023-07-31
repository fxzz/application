package com.example.application.account.service;

import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.domain.Account;

import java.util.Map;

public interface AccountService {

    void insertAccount(Account account);
    void saveAccount(AccountSignUpReqDto accountSignUpReqDto);
    int selectUsernameCount(String username);
    int selectEmailCount(String email);
    int selectNicknameCount(String nickname);
    Account selectUserByUsername(String username);
    AccountProfileRespDto selectUserByAccountId(Long accountId);
    AccountProfileRespDto getUserProfileByAccountId(Long accountId);
    void saveProfile(AccountProfileRespDto accountProfileRespDto, Long accountId);
    void updateProfile(Map map);
    void passwordChange(Account account, PasswordChangeReqDto passwordChangeReqDto);
    void updatePassword(Account account);
}
