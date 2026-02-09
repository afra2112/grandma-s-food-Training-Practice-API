package com.grandmasfood.v1.dto;

import java.math.BigDecimal;

public record MostOrLessSoldProductResponse(
        String productName,
        Integer selledUnits,
        BigDecimal basePrice
) {
}
