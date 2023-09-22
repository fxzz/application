package com.example.application.security;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static String getUsernameFromRequest(HttpServletRequest request) {
        return request.getParameter("username");
    }
}
