package com.empik.interview.coupon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 17:23
 */
@Table(name = "coupons")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name = "current_uses")
    private Integer currentUses;

    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;

    @Column(name = "used_at")
    private Instant usedAt;

    @Column(name = "created_at")
    private Instant createdAt;

    boolean active;

    public void incrementCurrentUses() {
        this.currentUses++;
    }
}
