package com.empik.interview.coupon.exception;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:38
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
