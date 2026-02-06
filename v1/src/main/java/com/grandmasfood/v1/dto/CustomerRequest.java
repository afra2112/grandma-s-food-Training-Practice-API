package com.grandmasfood.v1.dto;

import com.grandmasfood.v1.config.customBeans.Document;
import com.grandmasfood.v1.config.customBeans.PhoneNumber;
import jakarta.validation.constraints.*;

public record CustomerRequest(
        @Document
        String document,

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
