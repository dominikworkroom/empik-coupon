package com.empik.interview.coupon.api;

import lombok.Builder;

import java.time.Instant;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:10
 */
@Builder
public record ErrorApiResponse( Instant timestamp,
                                int status,
                                String error,
                                String message) {}
