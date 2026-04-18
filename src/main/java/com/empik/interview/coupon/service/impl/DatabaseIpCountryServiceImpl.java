package com.empik.interview.coupon.service.impl;

import com.empik.interview.coupon.entity.IpCountry;
import com.empik.interview.coupon.exception.CannotResolveIpCountryException;
import com.empik.interview.coupon.repository.IpCountryRepository;
import com.empik.interview.coupon.service.IpCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:35
 */
@Service
@RequiredArgsConstructor
public class DatabaseIpCountryServiceImpl implements IpCountryService {

    private final IpCountryRepository ipCountryTableRepository;

    @Override
    public String resolveCountryCode(String ipAddress) {
        return ipCountryTableRepository.findAllByOrderByIpPrefixDesc()
                .stream()
                .filter(entry -> ipAddress.startsWith(entry.getIpPrefix()))
                .map(IpCountry::getCountryCode)
                .findFirst()
                .orElseThrow(() -> new CannotResolveIpCountryException("Cannot resolve country for IP"));
    }
}
