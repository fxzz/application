package com.example.application.coupon.service;

import com.example.application.coupon.mapper.CouponMapper;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponMapper couponMapper;

    @Override
    public void issueCouponToUser(String code, UserAccount userAccount) {
        // 쿠폰 유효성 검증

        // 레디스 sorted set으로 대기열, 락

        //db 업데이트
    }
}
