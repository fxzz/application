package com.example.application.security;

import com.example.application.account.mapper.LoginFailureReadMapper;
import com.example.application.account.mapper.LoginFailureWriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final LoginFailureWriteMapper loginFailureWriteMapper;
    private final LoginFailureReadMapper loginFailureReadMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = RequestUtils.getUsernameFromRequest(request);
        if (userFailuresCount(username)) {
            userFailuresCountDelete(username);
        }


        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        if (savedRequest != null) {
            getRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }




    private boolean userFailuresCount(String username) {
        int count = loginFailureReadMapper.selectUsernameFailureCount(username);
        return count > 0;
    }

    private void userFailuresCountDelete(String username) {
        loginFailureWriteMapper.deleteUsernames(username);
    }
}
