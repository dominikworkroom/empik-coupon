package com.empik.interview.coupon.exception;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 19:05
 */
public class CannotResolveIpCountryException extends RuntimeException {
    public CannotResolveIpCountryException(String message) {
        super(message);
    }
}
