package com.empik.interview.coupon.utils;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:42
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CouponUtils {
    public static String normalizeCouponCode(String code) {
        return code.trim().toLowerCase(Locale.ROOT);
    }
}
