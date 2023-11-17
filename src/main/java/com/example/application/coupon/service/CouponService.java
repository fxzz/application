package com.example.application.coupon.service;

import com.example.application.security.UserAccount;

public interface CouponService {
    void issueCouponToUser(String code, UserAccount userAccount);
}
