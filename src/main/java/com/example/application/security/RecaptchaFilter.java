package com.example.application.security;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RecaptchaFilter extends OncePerRequestFilter {


    private static final String LOGIN_URL = "/login";
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String REDIRECT_LOGIN_ERROR_WITH_CAPTCHA = "/login?error&captchaFailure&captcha=true";

    @Value("${recaptchaSecretKey}")
    private String recaptchaSecretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (isLoginRecaptchaRequest(request)) {
            if (isValidCaptcha(request.getParameter("g-recaptcha-response"))) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect(REDIRECT_LOGIN_ERROR_WITH_CAPTCHA);
                return;

            }
        }


        filterChain.doFilter(request, response);
    }




    private boolean isValidCaptcha(String captchaResponse) {
        String params = "?secret=" + recaptchaSecretKey + "&response=" + captchaResponse;

        RestTemplate restTemplate = new RestTemplate();
        RecaptchaResponse response = restTemplate.getForObject(RECAPTCHA_VERIFY_URL + params, RecaptchaResponse.class);

        if (response != null && response.isSuccess()) {
            return true;
        }

        return false;
    }


    private boolean isLoginRecaptchaRequest(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("POST") && request.getRequestURI().equals(LOGIN_URL)) {
            String showCaptcha = request.getParameter("showCaptcha");
            return "true".equals(showCaptcha);
        }
        return false;
    }


    private static class RecaptchaResponse {
        private boolean success;

        public boolean isSuccess() {
            return success;
        }
    }
}
