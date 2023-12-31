package com.example.application.account.service;

import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.dto.AccountRespDto.*;
import com.example.application.account.dto.NotificationUpdateDto;
import com.example.application.account.mapper.AccountReadMapper;
import com.example.application.account.mapper.AccountWriteMapper;
import com.example.application.account.dto.Account;
import com.example.application.util.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        insertAccount(accountSignUpReqDto);
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
        updateProfile(accountProfileRespDto, accountId);
    }



    @Transactional
    @Override
    public void passwordChange(Account account, PasswordChangeReqDto passwordChangeReqDto) {
        updatePassword(account, passwordChangeReqDto);
    }


    @Transactional
    @Override
    public void updateNotificationEnabled(NotificationUpdateDto notificationUpdateDto) {
        accountWriteMapper.updateNotificationEnabled(notificationUpdateDto);
    }

    @Override
    public boolean selectNotificationEnabled(Long accountId) {
        return accountReadMapper.selectNotificationEnabled(accountId);
    }


    private void updatePassword(Account account, PasswordChangeReqDto passwordChangeReqDto) {
        if (passwordEncoder.matches(passwordChangeReqDto.getPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(passwordChangeReqDto.getNewPassword()));
            accountWriteMapper.updatePassword(account);
        }else {
            throw new PasswordMismatchException("현재 패스워드가 일치하지 않습니다.");
        }
    }

    private void updateProfile(AccountProfileRespDto accountProfileRespDto, Long accountId) {
        Map<String, Object> accountData = new HashMap<>();
        accountData.put("fullName", accountProfileRespDto.getFullName());
        accountData.put("nickname", accountProfileRespDto.getNickname());
        accountData.put("accountId", accountId);
        accountWriteMapper.updateProfile(accountData);
    }

    private void insertAccount(AccountSignUpReqDto accountSignUpReqDto) {
        var account = accountSignUpReqDto.toAccount();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountWriteMapper.insertAccount(account);
    }

}
