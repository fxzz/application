package com.example.application.account.mapper;

import com.example.application.account.dto.Account;
import org.apache.ibatis.annotations.Mapper;
import com.example.application.account.dto.AccountRespDto.*;

@Mapper
public interface AccountReadMapper {

    int selectUsernameCount(String username);
    int selectEmailCount(String email);
    int selectNicknameCount(String nickname);
    Account selectUserByUsername(String username);
    AccountProfileRespDto selectUserByAccountId(Long accountId);

}
