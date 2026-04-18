package com.empik.interview.coupon.service;

import com.empik.interview.coupon.request.CreateUserRequest;
import com.empik.interview.coupon.response.UserResponse;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:57
 */
public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}
