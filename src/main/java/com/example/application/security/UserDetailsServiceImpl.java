package com.example.application.security;

import com.example.application.account.service.AccountService;
import com.example.application.account.dto.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Account account = accountService.selectUserByUsername(username);

      if (account == null) {
          throw new UsernameNotFoundException(username);
      }

        return new UserAccount(account);
    }
}
