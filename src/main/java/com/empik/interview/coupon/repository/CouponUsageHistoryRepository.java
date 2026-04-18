package com.empik.interview.coupon.repository;

import com.empik.interview.coupon.entity.Coupon;
import com.empik.interview.coupon.entity.CouponUsageHistory;
import com.empik.interview.coupon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:07
 */
public interface CouponUsageHistoryRepository extends JpaRepository<CouponUsageHistory, Long> {
    boolean existsByUserAndCoupon(User user, Coupon coupon);
}
