package com.empik.interview.coupon.service.impl;

import com.empik.interview.coupon.converter.CouponConverter;
import com.empik.interview.coupon.converter.CouponUsageConverter;
import com.empik.interview.coupon.entity.Coupon;
import com.empik.interview.coupon.entity.CouponUsageHistory;
import com.empik.interview.coupon.entity.User;
import com.empik.interview.coupon.exception.CouponNotFoundException;
import com.empik.interview.coupon.exception.CouponValidationException;
import com.empik.interview.coupon.exception.UserNotFoundException;
import com.empik.interview.coupon.repository.CouponRepository;
import com.empik.interview.coupon.repository.CouponUsageHistoryRepository;
import com.empik.interview.coupon.repository.UserRepository;
import com.empik.interview.coupon.request.CreateCouponRequest;
import com.empik.interview.coupon.request.ReedemCouponRequest;
import com.empik.interview.coupon.response.CouponResponse;
import com.empik.interview.coupon.response.CouponUsageResponse;
import com.empik.interview.coupon.service.CouponService;
import com.empik.interview.coupon.utils.CouponUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 17:22
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponUsageHistoryRepository couponUsageHistoryRepository;
    private final UserRepository userRepository;
    private final CouponConverter couponConverter;
    private final DatabaseIpCountryServiceImpl ipCountryService;
    private final CouponUsageConverter couponUsageConverter;

    @Override
    @Transactional
    public CouponResponse createCoupon(CreateCouponRequest request) {
        String normalizedCode = CouponUtils.normalizeCouponCode(request.code());

        if (couponRepository.existsByCode(normalizedCode)) {
            throw new CouponValidationException("Coupon code already exists", HttpStatus.CONFLICT);
        }

        Coupon coupon = couponConverter.convertToCoupon(request);
        coupon.setCreatedAt(Instant.now());
        coupon.setCurrentUses(0);
        coupon.setActive(true);
        return couponConverter.convertToCouponResponse(couponRepository.save(coupon));
    }

    @Override
    @Transactional
    public CouponUsageResponse redeemCoupon(ReedemCouponRequest request, String ip) {
        User user = getUser(request.userId());
        Coupon coupon = couponRepository.findByCodeForUpdate(CouponUtils.normalizeCouponCode(request.code()))
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));
        String requestCountryCode = ipCountryService.resolveCountryCode(ip);

        validateCouponRedeem(user, coupon, requestCountryCode);

        coupon.incrementCurrentUses();

        CouponUsageHistory usage = couponUsageHistoryRepository.save(
                CouponUsageHistory.builder()
                        .user(user)
                        .coupon(coupon)
                        .usedAt(Instant.now())
                        .build()
        );

        return couponUsageConverter.convertToCouponUsageResponse(usage);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void validateCouponRedeem(User user, Coupon coupon, String requestCountryCode) {
        validateCouponCountry(coupon, requestCountryCode);
        validateUserCountry(user, requestCountryCode);
        validateUserDidNotUseCoupon(user, coupon);
        validateCouponLimit(coupon);
    }


    private void validateCouponCountry(Coupon coupon, String requestCountryCode) {
        if (!coupon.getCountryCode().equalsIgnoreCase(requestCountryCode)) {
            throw new CouponValidationException("Coupon cannot be used from this country", HttpStatus.FORBIDDEN);
        }
    }

    private void validateUserCountry(User user, String requestCountryCode) {
        if (!user.getCountryCode().equalsIgnoreCase(requestCountryCode)) {
            throw new CouponValidationException("User country does not match request country", HttpStatus.FORBIDDEN);
        }
    }

    private void validateUserDidNotUseCoupon(User user, Coupon coupon) {
        if (couponUsageHistoryRepository.existsByUserAndCoupon(user, coupon)) {
            throw new CouponValidationException("User already used this coupon", HttpStatus.CONFLICT);
        }
    }

    private void validateCouponLimit(Coupon coupon) {
        if (coupon.getCurrentUses() >= coupon.getMaxUses()) {
            throw new CouponValidationException("Coupon usage limit reached", HttpStatus.CONFLICT);
        }
    }
}
