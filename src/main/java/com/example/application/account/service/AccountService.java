package com.example.application.account.service;

import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.account.dto.Account;

public interface AccountService {


    void saveAccount(AccountSignUpReqDto accountSignUpReqDto);
    int selectUsernameCount(String username);
    int selectEmailCount(String email);
    int selectNicknameCount(String nickname);
    Account selectUserByUsername(String username);
    AccountProfileRespDto getUserProfileByAccountId(Long accountId);
    void saveProfile(AccountProfileRespDto accountProfileRespDto, Long accountId);
    void passwordChange(Account account, PasswordChangeReqDto passwordChangeReqDto);

}
