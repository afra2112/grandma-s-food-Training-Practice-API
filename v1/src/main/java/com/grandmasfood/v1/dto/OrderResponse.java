package com.grandmasfood.v1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse (
        String customerDocument,
        UUID productId,
        int quantity,
        String additionalInfo,
        CustomerResponse customer,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total,
        boolean delivered,
        LocalDateTime deliveryDate
){
}
