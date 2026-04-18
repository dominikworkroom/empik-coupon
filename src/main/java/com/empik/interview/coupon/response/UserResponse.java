package com.empik.interview.coupon.response;

import lombok.Builder;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:04
 */
@Builder
public record UserResponse(Long id, String username, String name, String surname, String countryCode) {
}
