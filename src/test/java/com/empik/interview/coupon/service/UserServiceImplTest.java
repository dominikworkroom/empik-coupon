package com.empik.interview.coupon.service;

import com.empik.interview.coupon.converter.UserConverter;
import com.empik.interview.coupon.entity.User;
import com.empik.interview.coupon.repository.UserRepository;
import com.empik.interview.coupon.request.CreateUserRequest;
import com.empik.interview.coupon.response.UserResponse;
import com.empik.interview.coupon.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Dominik Zieliński
 * Date: 18/04/2026
 * Time: 08:46
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserShouldSaveTrimmedValuesAndReturnResponse() {
        CreateUserRequest request = new CreateUserRequest(" john ", " John ", " Doe ", " PL ");

        User savedUser = User.builder()
                .id(1L)
                .username("john")
                .name("John")
                .surname("Doe")
                .countryCode("PL")
                .build();

        UserResponse expectedResponse = UserResponse.builder()
                .id(1L)
                .username("john")
                .name("John")
                .surname("Doe")
                .countryCode("PL")
                .build();

        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(savedUser);
        when(userConverter.convertToUserResponse(savedUser)).thenReturn(expectedResponse);

        UserResponse actual = userService.createUser(request);

        assertEquals(expectedResponse, actual);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User toSave = userCaptor.getValue();

        assertEquals("john", toSave.getUsername());
        assertEquals("John", toSave.getName());
        assertEquals("Doe", toSave.getSurname());
        assertEquals("PL", toSave.getCountryCode());
        assertNotNull(toSave.getCreatedAt());
    }
}
