package com.example.application.coupon.controller;

import com.example.application.coupon.service.CouponService;
import com.example.application.security.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupon")
    public String couponForm() {
        return "coupon-test";
    }

    @PostMapping("/coupon")
    public String issueCoupon(@RequestParam String code, @AuthenticationPrincipal UserAccount userAccount) {

        couponService.issueCouponToUser(code, userAccount);

        return "redirect:/";
    }



}
