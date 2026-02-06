package com.grandmasfood.v1.dto;

import com.grandmasfood.v1.config.enums.ProductCategoryEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse (
        UUID productUUID,
        String fantasyName,
        ProductCategoryEnum category,
        String description,
        BigDecimal price,
        boolean available
) {
}
