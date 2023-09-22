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
        String username = RequestUtils.getUsernameFromRequest(request);
        if (!username.isEmpty()) {
            if (checkUserExistsByUsername(username)) {
                loginFailureWriteMapper.insertUsernameFailure(username);
            }
        }

        if (userLoginFailuresCount(username)) {
            setDefaultFailureUrl("/login?error&captcha=true");
        }
        super.onAuthenticationFailure(request, response, exception);
    }


    private boolean checkUserExistsByUsername(String username) {
        int count = accountService.selectUsernameCount(username);
        return count > 0;
    }

    private boolean userLoginFailuresCount(String username) {
        int count = loginFailureReadMapper.selectUsernameFailureCount(username);
        return count > 3;
    }
}
