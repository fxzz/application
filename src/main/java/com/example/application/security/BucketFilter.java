package com.example.application.security;


import com.example.application.account.dto.Account;
import com.example.application.util.exception.InvalidAccessOperationException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class BucketFilter extends OncePerRequestFilter {

    private Map<Long, Bucket> userBuckets = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isAuthenticatedUser(authentication) && isHttpPostRequest(request)) {
            Bucket bucket = getUserBucket(getAccountId(authentication));
            if (consumeToken(bucket, response)) {
                filterChain.doFilter(request, response);
            }
        } else {
            // POST 요청이 아닌 경우에는 토큰을 소비하지 않고 처리
            filterChain.doFilter(request, response);
        }
    }

    //fly.io 는 일정시간 사용 안하면 서버가 꺼져서 의미가 없지만 이런 방법도 있다는 것을 기록
    @Scheduled(fixedRate = 3600000) //1시간 마다 메서드 실행
    public void cleanupExpiredBuckets() {
        userBuckets.clear();
    }


    private boolean consumeToken(Bucket bucket, HttpServletResponse response) {
        if (bucket.tryConsume(1)) {
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            throw new RuntimeException("너무 많은 POST 요청을 보냈습니다. 잠시 후 다시 시도 해주세요.");
        }
    }

    private boolean isHttpPostRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("POST");
    }

    private boolean isAuthenticatedUser(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() instanceof UserAccount;
    }

    private Long getAccountId(Authentication authentication) {
        return ((UserAccount) authentication.getPrincipal()).getAccount().getAccountId();
    }

    private Bucket getUserBucket(Long accountId) {
        if (!userBuckets.containsKey(accountId)) {
            userBuckets.put(accountId, createBucket());
        }
        return userBuckets.get(accountId);
    }

    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(15, Refill.intervally(15, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

}