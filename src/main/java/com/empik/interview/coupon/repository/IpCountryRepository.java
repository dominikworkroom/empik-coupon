package com.empik.interview.coupon.repository;

import com.empik.interview.coupon.entity.IpCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 19:00
 */
public interface IpCountryRepository extends JpaRepository<IpCountry, String> {
    List<IpCountry> findAllByOrderByIpPrefixDesc();
}
