package com.empik.interview.coupon.converter;

import com.empik.interview.coupon.entity.Coupon;
import com.empik.interview.coupon.request.CreateCouponRequest;
import com.empik.interview.coupon.response.CouponResponse;
import org.springframework.stereotype.Component;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:46
 */
@Component
public class CouponConverter {

    public Coupon convertToCoupon(CreateCouponRequest couponRequest) {
        return Coupon.builder()
                .code(couponRequest.code())
                .maxUses(couponRequest.maxUses())
                .countryCode(couponRequest.countryCode())
                .build();
    }

    public CouponResponse convertToCouponResponse(Coupon coupon) {
        return CouponResponse.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .createdAt(coupon.getCreatedAt())
                .maxUsages(coupon.getMaxUses())
                .currentUsages(coupon.getCurrentUses())
                .countryCode(coupon.getCountryCode())
                .build();
    }

}
