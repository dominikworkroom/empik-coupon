package com.empik.interview.coupon.service;

import com.empik.interview.coupon.request.CreateCouponRequest;
import com.empik.interview.coupon.request.ReedemCouponRequest;
import com.empik.interview.coupon.response.CouponResponse;
import com.empik.interview.coupon.response.CouponUsageResponse;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:56
 */
public interface CouponService {
    CouponUsageResponse redeemCoupon(ReedemCouponRequest request, String ip);
    CouponResponse createCoupon(CreateCouponRequest request);
}
