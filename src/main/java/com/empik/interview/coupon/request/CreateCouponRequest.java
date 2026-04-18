package com.empik.interview.coupon.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 17:21
 */
public record CreateCouponRequest(@NotBlank(message = "Code cannot be empty") String code, Integer maxUses,
                                  @NotBlank(message = "Target country cannot be empty")
                                  @Pattern(regexp = "^[A-Z]{2}$", message = "Country Code must be 2 uppercase letters") String countryCode) {

}
