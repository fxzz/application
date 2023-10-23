package com.example.application;

import com.example.application.account.dto.AccountReqDto;
import com.example.application.account.dto.AccountReqDto.*;
import com.example.application.account.service.AccountService;
import com.example.application.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final UserDetailsServiceImpl userDetailsService;
    private final AccountService accountService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {

      String username = withAccount.value();

        var  accountSignUpReqDto = AccountSignUpReqDto.builder()
                .username(username)
                .password("12345")
                .email(username + "@hhhhh.com")
                .fullName(username)
                .nickname(username)
                .build();


        accountService.saveAccount(accountSignUpReqDto);


      UserDetails principal = userDetailsService.loadUserByUsername(username);
      Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);
      return context;
    }
}
