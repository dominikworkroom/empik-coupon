package com.empik.interview.coupon.service.impl;

import com.empik.interview.coupon.converter.UserConverter;
import com.empik.interview.coupon.entity.User;
import com.empik.interview.coupon.repository.UserRepository;
import com.empik.interview.coupon.request.CreateUserRequest;
import com.empik.interview.coupon.response.UserResponse;
import com.empik.interview.coupon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:15
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User entity = User.builder()
                .username(request.username().trim())
                .name(request.name().trim())
                .surname(request.surname().trim())
                .countryCode(request.countryCode().trim())
                .createdAt(Instant.now())
                .build();

        return userConverter.convertToUserResponse(userRepository.save(entity));
    }
}
