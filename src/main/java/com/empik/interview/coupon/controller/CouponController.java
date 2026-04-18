package com.empik.interview.coupon.controller;

import com.empik.interview.coupon.request.CreateCouponRequest;
import com.empik.interview.coupon.request.ReedemCouponRequest;
import com.empik.interview.coupon.resolver.IpResolver;
import com.empik.interview.coupon.response.CouponResponse;
import com.empik.interview.coupon.response.CouponUsageResponse;
import com.empik.interview.coupon.service.CouponService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 17:15
 */
@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final IpResolver ipResolver;

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@Valid @RequestBody CreateCouponRequest request) {
        CouponResponse response = couponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/use")
    public ResponseEntity<CouponUsageResponse> useCoupon(@RequestBody @Valid ReedemCouponRequest request,
                                                         HttpServletRequest servletRequest) {

        String ip = ipResolver.resolve(servletRequest);
        CouponUsageResponse response = couponService.redeemCoupon(request, ip);
        return ResponseEntity.ok(response);
    }
}
