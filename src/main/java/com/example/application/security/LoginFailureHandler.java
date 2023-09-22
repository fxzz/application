package com.example.application.security;

import com.example.application.account.mapper.LoginFailureReadMapper;
import com.example.application.account.mapper.LoginFailureWriteMapper;
import com.example.application.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final AccountService accountService;
    private final LoginFailureWriteMapper loginFailureWriteMapper;
    private final LoginFailureReadMapper loginFailureReadMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");
        String username = request.getParameter("username");
        if (!username.isEmpty()) {
            if (accountService.selectUsernameCount(username) > 0) {
                loginFailureWriteMapper.insertUsernameFailure(username);
            }
        }

        if (loginFailureReadMapper.selectUsernameFailureCount(username) > 5) {
            setDefaultFailureUrl("/login?error&captcha=true");
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
