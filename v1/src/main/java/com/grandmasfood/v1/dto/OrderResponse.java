package com.grandmasfood.v1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse (
        UUID orderUUID,
        LocalDateTime orderCreatedAt,
        String customerDocument,
        UUID productUUID,
        int quantity,
        String additionalInfo,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total,
        boolean delivered,
        LocalDateTime deliveryDate
){
}
