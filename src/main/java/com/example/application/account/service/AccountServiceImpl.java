package com.example.application.account.service;

import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.account.mapper.AccountReadMapper;
import com.example.application.account.mapper.AccountWriteMapper;
import com.example.application.domain.Account;
import com.example.application.domain.Role;
import com.example.application.util.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountReadMapper accountReadMapper;
    private final AccountWriteMapper accountWriteMapper;



    @Transactional
    @Override
    public void saveAccount(AccountSignUpReqDto accountSignUpReqDto) {
      Account account = Account.builder()
              .username(accountSignUpReqDto.getUsername())
              .password(passwordEncoder.encode(accountSignUpReqDto.getPassword()))
              .email(accountSignUpReqDto.getEmail())
              .fullName(accountSignUpReqDto.getFullName())
              .nickname(accountSignUpReqDto.getNickname())
              .role(Role.USER)
              .createdAt(LocalDateTime.now())
              .build();

      accountWriteMapper.insertAccount(account);
    }


    @Override
    public int selectUsernameCount(String username) {
        return accountReadMapper.selectUsernameCount(username);
    }


    @Override
    public int selectEmailCount(String email) {
        return accountReadMapper.selectEmailCount(email);
    }


    @Override
    public int selectNicknameCount(String nickname) {
        return accountReadMapper.selectNicknameCount(nickname);
    }


    @Override
    public Account selectUserByUsername(String username) {
        return accountReadMapper.selectUserByUsername(username);
    }


    @Override
    public AccountProfileRespDto getUserProfileByAccountId(Long accountId) {
        return accountReadMapper.selectUserByAccountId(accountId);
    }

    @Transactional
    @Override
    public void saveProfile(AccountProfileRespDto accountProfileRespDto, Long accountId) {
        Map<String, Object> accountData = new HashMap<>();
        accountData.put("fullName", accountProfileRespDto.getFullName());
        accountData.put("nickname", accountProfileRespDto.getNickname());
        accountData.put("accountId", accountId);
        accountWriteMapper.updateProfile(accountData);
    }


    @Transactional
    @Override
    public void passwordChange(Account account, PasswordChangeReqDto passwordChangeReqDto) {
        if (passwordEncoder.matches(passwordChangeReqDto.getPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(passwordChangeReqDto.getNewPassword()));
            accountWriteMapper.updatePassword(account);
        }else {
            throw new PasswordMismatchException("현재 패스워드가 일치하지 않습니다.");
        }
    }


}
