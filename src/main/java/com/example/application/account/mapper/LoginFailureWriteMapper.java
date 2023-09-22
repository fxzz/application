package com.example.application.account.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginFailureWriteMapper {
   void insertUsernameFailure(String username);
}
