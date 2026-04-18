package com.empik.interview.coupon.repository;

import com.empik.interview.coupon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:08
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
