package com.example.application.security;

import com.example.application.account.dto.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
public class UserAccount extends User implements OAuth2User {

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


    @Override
    public Map<String, Object> getAttributes() {
        return account.getOAuth2Attributes();
    }

    @Override
    public String getName() {
        return account.getUsername();
    }
}
