package com.example.application.account.mapper;


import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.domain.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface AccountMapper {

    void insertAccount(Account account);
    int selectUsernameCount(String username);
    int selectEmailCount(String email);
    int selectNicknameCount(String nickname);
    Account selectUserByUsername(String username);
    AccountProfileRespDto selectUserByAccountId(Long accountId);
    void updateProfile(Map map);
    void updatePassword(Account account);


}
