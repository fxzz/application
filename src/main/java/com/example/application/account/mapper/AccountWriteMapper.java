package com.example.application.account.mapper;

import com.example.application.domain.Account;
import org.apache.ibatis.annotations.Mapper;


import java.util.Map;

@Mapper
public interface AccountWriteMapper {

    void insertAccount(Account account);
    void updateProfile(Map map);
    void updatePassword(Account account);
}
