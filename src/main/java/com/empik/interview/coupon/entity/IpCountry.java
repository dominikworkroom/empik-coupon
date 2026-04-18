package com.empik.interview.coupon.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:36
 */
@Table(name = "ip_country_table")
@Data
@Entity
public class IpCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ipPrefix;

    @Column(nullable = false, length = 2)
    private String countryCode;
}
