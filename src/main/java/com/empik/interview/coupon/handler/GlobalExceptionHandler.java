package com.empik.interview.coupon.handler;

import com.empik.interview.coupon.api.ErrorApiResponse;
import com.empik.interview.coupon.exception.CannotResolveIpCountryException;
import com.empik.interview.coupon.exception.CouponNotFoundException;
import com.empik.interview.coupon.exception.CouponValidationException;
import com.empik.interview.coupon.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:09
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleCouponNotFoundException(CouponNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(CouponValidationException.class)
    public ResponseEntity<ErrorApiResponse> handleCouponValidationException(CouponValidationException ex) {
        return buildErrorResponse(ex.getStatus(), ex.getStatus().name(), ex.getMessage());
    }

    @ExceptionHandler(CannotResolveIpCountryException.class)
    public ResponseEntity<ErrorApiResponse> handleCannotResolveIpCountryException(CannotResolveIpCountryException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), ex.getMessage());
    }

    private ResponseEntity<ErrorApiResponse> buildErrorResponse(HttpStatus status, String errorCode, String message) {
        return ResponseEntity.status(status)
                .body(ErrorApiResponse.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .error(errorCode)
                        .message(message)
                        .build());
    }
}
