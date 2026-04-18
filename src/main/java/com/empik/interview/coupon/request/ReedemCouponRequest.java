package com.empik.interview.coupon.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 17:17
 */
public record ReedemCouponRequest(@NotNull(message = "User ID cannot be empty")  Long userId, @NotBlank(message = "Code cannot be empty") @Size(max = 100) String code) {
}
