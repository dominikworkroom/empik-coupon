package com.empik.interview.coupon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:33
 */
@Getter
public class CouponValidationException extends RuntimeException {
    private final HttpStatus status;

    public CouponValidationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
