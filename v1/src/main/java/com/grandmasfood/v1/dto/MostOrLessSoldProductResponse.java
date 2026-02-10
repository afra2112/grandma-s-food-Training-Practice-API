package com.grandmasfood.v1.dto;

import java.math.BigDecimal;

public record MostOrLessSoldProductResponse(
        String productName,
        Long soldUnits,
        BigDecimal basePrice
) {
    public BigDecimal getGrossIncome(){
        return basePrice.multiply(BigDecimal.valueOf(soldUnits));
    }
}
