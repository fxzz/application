package com.example.application.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class RecaptchaFilter extends OncePerRequestFilter {

    private String loginUrl;
    private String failureUrl;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (isLoginRecaptchaRequest(request)) {
            System.out.println("실행");

        }


        filterChain.doFilter(request, response);
    }


    private boolean isLoginRecaptchaRequest(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("POST") && request.getRequestURI().equals(loginUrl)) {
            String showCaptcha = request.getParameter("showCaptcha");
            return "true".equals(showCaptcha);
        }
        return false;
    }
}
