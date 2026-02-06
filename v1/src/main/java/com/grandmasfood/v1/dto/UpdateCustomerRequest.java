package com.grandmasfood.v1.dto;

import com.grandmasfood.v1.config.customBeans.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
        @NotBlank
        String nameAndSurname,

        @Email
        @NotBlank
        String email,

        @PhoneNumber
        String phoneNumber,

        @NotBlank
        @Size(max = 500)
        String shippingAddress
) {
}