package com.example.application.security;

import com.example.application.account.dto.Account;
import com.example.application.account.mapper.AccountReadMapper;
import com.example.application.account.mapper.AccountWriteMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountReadMapper accountReadMapper;
    private final AccountWriteMapper accountWriteMapper;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        KakaoOAuth2Response kakaoResponse = KakaoOAuth2Response.from(oAuth2User.getAttributes());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String providerId = String.valueOf(kakaoResponse.getId());
        String username = registrationId + "_" + providerId;
        String dummyPassword = passwordEncoder.encode(UUID.randomUUID().toString());


        Account account = accountReadMapper.selectUserByUsername(username);
        if (account != null) {
            return new UserAccount(account);
        } else {
            var newAccount = Account.builder()
                    .username(username)
                    .password(dummyPassword)
                    .email(kakaoResponse.email())
                    .nickname(kakaoResponse.nickname()+"_kakao")
                    .fullName(kakaoResponse.nickname())
                    .role(Account.Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();


            accountWriteMapper.insertAccount(newAccount);

            var kakaoAccount = accountReadMapper.selectUserByUsername(newAccount.getUsername());
            return new UserAccount(kakaoAccount);
        }
    }

}