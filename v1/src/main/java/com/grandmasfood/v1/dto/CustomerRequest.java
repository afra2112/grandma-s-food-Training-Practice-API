package com.grandmasfood.v1.dto;

import jakarta.validation.constraints.*;

public record CustomerRequest(
        @NotBlank
        @Pattern(
                regexp = "^(CC|TI|CE|P)-\\d{1,17}$",
                message = "The customer document must be like (document type-number) ex: 'CC-1234567890'"
        )
        @Size(max = 20)
        String document,

        @NotBlank
        String nameAndSurname,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "phone number must have 10 digits, ex: 3101234567"
        )
        @Size(max = 10)
        String phoneNumber,

        @NotBlank
        @Size(max = 500)
        String shippingAddress
) {
}
