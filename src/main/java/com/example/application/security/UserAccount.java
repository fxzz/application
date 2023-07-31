package com.example.application.security;

import com.example.application.account.dto.Account;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
public class UserAccount extends User {

    private Account account;

    public UserAccount(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList("ROLE_"+account.getRole().toString()));
        this.account = account;
    }

    public Long accountId() {
        return account.getAccountId();
    }

    public String nickname() {
        return account.getNickname();
    }

}
