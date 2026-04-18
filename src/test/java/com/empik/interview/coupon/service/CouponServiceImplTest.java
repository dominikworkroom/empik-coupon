package com.empik.interview.coupon.service;

import com.empik.interview.coupon.converter.CouponConverter;
import com.empik.interview.coupon.converter.CouponUsageConverter;
import com.empik.interview.coupon.entity.Coupon;
import com.empik.interview.coupon.entity.CouponUsageHistory;
import com.empik.interview.coupon.entity.User;
import com.empik.interview.coupon.exception.CouponValidationException;
import com.empik.interview.coupon.exception.UserNotFoundException;
import com.empik.interview.coupon.repository.CouponRepository;
import com.empik.interview.coupon.repository.CouponUsageHistoryRepository;
import com.empik.interview.coupon.repository.UserRepository;
import com.empik.interview.coupon.request.CreateCouponRequest;
import com.empik.interview.coupon.request.ReedemCouponRequest;
import com.empik.interview.coupon.response.CouponResponse;
import com.empik.interview.coupon.response.CouponUsageResponse;
import com.empik.interview.coupon.service.impl.CouponServiceImpl;
import com.empik.interview.coupon.service.impl.DatabaseIpCountryServiceImpl;
import com.empik.interview.coupon.utils.CouponUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private CouponUsageHistoryRepository couponUsageHistoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CouponConverter couponConverter;
    @Mock
    private DatabaseIpCountryServiceImpl ipCountryService;
    @Mock
    private CouponUsageConverter couponUsageConverter;

    @InjectMocks
    private CouponServiceImpl couponService;

    @Test
    void createCouponShouldSaveCouponAndReturnResponse() {
        CreateCouponRequest request = new CreateCouponRequest("WELCOME10", 5, "PL");
        Coupon coupon = Coupon.builder()
                .code("WELCOME10")
                .maxUses(5)
                .countryCode("PL")
                .build();
        Coupon saved = Coupon.builder()
                .id(10L)
                .code("WELCOME10")
                .maxUses(5)
                .countryCode("PL")
                .currentUses(0)
                .active(true)
                .createdAt(Instant.now())
                .build();
        CouponResponse expected = CouponResponse.builder()
                .id(10L)
                .code("WELCOME10")
                .maxUsages(5)
                .currentUsages(0)
                .countryCode("PL")
                .createdAt(saved.getCreatedAt())
                .build();

        when(couponRepository.existsByCode(CouponUtils.normalizeCouponCode("WELCOME10"))).thenReturn(false);
        when(couponConverter.convertToCoupon(request)).thenReturn(coupon);
        when(couponRepository.save(coupon)).thenReturn(saved);
        when(couponConverter.convertToCouponResponse(saved)).thenReturn(expected);

        CouponResponse actual = couponService.createCoupon(request);

        assertEquals(expected, actual);
        assertEquals(0, coupon.getCurrentUses());
        assertTrue(coupon.isActive());
        assertNotNull(coupon.getCreatedAt());
    }

    @Test
    void createCouponWhenCodeAlreadyExistsShouldThrowValidationException() {
        CreateCouponRequest request = new CreateCouponRequest("WELCOME10", 5, "PL");
        when(couponRepository.existsByCode(CouponUtils.normalizeCouponCode("WELCOME10"))).thenReturn(true);

        CouponValidationException ex = assertThrows(CouponValidationException.class,
                () -> couponService.createCoupon(request));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals("Coupon code already exists", ex.getMessage());
    }

    @Test
    void redeemCouponShouldIncrementUsageAndReturnResponse() {
        ReedemCouponRequest request = new ReedemCouponRequest(1L, "WELCOME10");
        String ip = "10.0.0.15";

        User user = User.builder().id(1L).countryCode("PL").build();
        Coupon coupon = Coupon.builder()
                .id(2L)
                .code("WELCOME10")
                .countryCode("PL")
                .currentUses(0)
                .maxUses(2)
                .build();
        CouponUsageHistory usage = CouponUsageHistory.builder()
                .id(100L)
                .user(user)
                .coupon(coupon)
                .usedAt(Instant.now())
                .build();
        CouponUsageResponse expected = CouponUsageResponse.builder()
                .couponId(2L)
                .couponCode("WELCOME10")
                .userId(1L)
                .usedAt(usage.getUsedAt())
                .currentUsages(1)
                .maxUsages(2)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(couponRepository.findByCodeForUpdate(CouponUtils.normalizeCouponCode("WELCOME10"))).thenReturn(Optional.of(coupon));
        when(ipCountryService.resolveCountryCode(ip)).thenReturn("PL");
        when(couponUsageHistoryRepository.existsByUserAndCoupon(user, coupon)).thenReturn(false);
        when(couponUsageHistoryRepository.save(any(CouponUsageHistory.class))).thenReturn(usage);
        when(couponUsageConverter.convertToCouponUsageResponse(usage)).thenReturn(expected);

        CouponUsageResponse actual = couponService.redeemCoupon(request, ip);

        assertEquals(expected, actual);
        assertEquals(1, coupon.getCurrentUses());
        verify(couponUsageHistoryRepository).save(any(CouponUsageHistory.class));
    }

    @Test
    void redeemCouponWhenUserDoesNotExistShouldThrowUserNotFoundException() {
        ReedemCouponRequest request = new ReedemCouponRequest(99L, "WELCOME10");
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> couponService.redeemCoupon(request, "10.0.0.1"));

        assertEquals("User not found", ex.getMessage());
    }
}
