package com.empik.interview.coupon.response;

import lombok.Builder;

import java.time.Instant;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:21
 */
@Builder
public record CouponResponse(
        Long id,
        String code,
        Instant createdAt,
        Integer maxUsages,
        Integer currentUsages,
        String countryCode
) {}
