package com.grandmasfood.v1.dto;

public record CustomerResponse(
        String customerDocument,
        String customerNameAndSurname,
        String customerEmail,
        String phoneNumber,
        String shippingAddress
) {
}
