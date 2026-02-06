package com.grandmasfood.v1.dto;

public record CustomerResponse(
        Long customerId,
        String customerDocument,
        String customerEmail,
        String phoneNumber,
        String shippingAddress
) {
}
