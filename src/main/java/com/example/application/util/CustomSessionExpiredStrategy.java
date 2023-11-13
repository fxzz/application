package com.example.application.util;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {
    private String expiredUrl;

    public CustomSessionExpiredStrategy(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        // 세션 만료 시 수행할 동작을 여기에 구현
        // 예: 특정 URL로 리다이렉션
        event.getResponse().sendRedirect(expiredUrl);
    }
}
