package com.empik.interview.coupon.converter;

import com.empik.interview.coupon.entity.CouponUsageHistory;
import com.empik.interview.coupon.response.CouponUsageResponse;
import org.springframework.stereotype.Component;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 19:06
 */
@Component
public class CouponUsageConverter {

    public CouponUsageResponse convertToCouponUsageResponse(CouponUsageHistory usage) {
        return CouponUsageResponse.builder()
                .couponId(usage.getCoupon().getId())
                .couponCode(usage.getCoupon().getCode())
                .userId(usage.getUser().getId())
                .usedAt(usage.getUsedAt())
                .currentUsages(usage.getCoupon().getCurrentUses())
                .maxUsages(usage.getCoupon().getMaxUses())
                .build();
    }
}
