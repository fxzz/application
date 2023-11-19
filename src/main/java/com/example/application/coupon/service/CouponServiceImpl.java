package com.example.application.coupon.service;

import com.example.application.coupon.mapper.CouponMapper;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {

    private static final String COUPON_COUNT_KEY = "_coupon_count";
    private static final long MAX_COUPON_COUNT = 10;
    
    
  //  private final CouponMapper couponMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void issueCouponToUser(String code, UserAccount userAccount) {
        // 쿠폰 유효성 검증
        // 쿠폰 code DB에 유효 한지 체크 하고 남은 수량 반환 받아서 동적으로 바꾸기 위해 MAX_COUPON_COUNT 를 지우고 변경

        // 쿠폰 카운터를 1증가하고 반환받음
        Long couponCount = incrementCouponCount(code);

        // 현재 발급된 쿠폰 수 체크
        if (couponCount != null && couponCount <= MAX_COUPON_COUNT) {
            // DB 에서 쿠폰 발급

            log.info("쿠폰 발급되었습니다.");
        } else {
            log.info("쿠폰 없습니다.");
           return;
        }
    }

    private Long incrementCouponCount(String code) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        Long couponCount = valueOps.increment(code + COUPON_COUNT_KEY);
        return couponCount;
    }
}
