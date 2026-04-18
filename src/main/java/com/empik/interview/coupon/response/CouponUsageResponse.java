package com.empik.interview.coupon.response;

import lombok.Builder;

import java.time.Instant;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:05
 */
@Builder
public record CouponUsageResponse(
        Long couponId,
        String couponCode,
        Long userId,
        Instant usedAt,
        Integer currentUsages,
        Integer maxUsages
) {
}
