package com.empik.interview.coupon.exception;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:13
 */
public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(String message) {
        super(message);
    }
}
