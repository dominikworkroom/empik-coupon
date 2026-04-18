package com.empik.interview.coupon.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Created by Dominik Zieliński
 * Date: 17/04/2026
 * Time: 17:45
 */
public record CreateUserRequest(@NotBlank @Size(max=50) String username, @NotBlank String name,  @NotBlank String surname,@NotBlank @Pattern(regexp = "^[A-Z]{2}$", message = "Country Code must be 2 uppercase letters") String countryCode) {
}
