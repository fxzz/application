package com.example.application;

import com.example.application.account.dto.Account;
import com.example.application.coupon.service.CouponService;
import com.example.application.coupon.service.CouponServiceImpl;
import com.example.application.security.UserAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CouponServiceImplTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CouponService couponService;


    @Test
    void testIssueCoupon() throws InterruptedException {
        int threadCount = 100; // 원하는 스레드 수 설정
        String testCode = "testCode";
        UserAccount fakeUserAccount = mock(UserAccount.class);

        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(30);

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            //병렬처리
            executorService.submit(() -> {
                try {
                    couponService.issueCouponToUser(testCode, fakeUserAccount);
                } finally {
                    //스레드 모든작업 완료까지 대기
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        redisTemplate.delete(testCode + "_coupon_count");
    }

}