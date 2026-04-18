package com.empik.interview.coupon.converter;

import com.empik.interview.coupon.entity.User;
import com.empik.interview.coupon.request.CreateUserRequest;
import com.empik.interview.coupon.response.UserResponse;
import org.springframework.stereotype.Component;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 18:25
 */
@Component
public class UserConverter {

    public User convertToUser(CreateUserRequest userRequest) {
        return User.builder()
                .username(userRequest.username())
                .name(userRequest.name())
                .surname(userRequest.surname())
                .build();
    }

    public UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .countryCode(user.getCountryCode())
                .build();
    }
}
