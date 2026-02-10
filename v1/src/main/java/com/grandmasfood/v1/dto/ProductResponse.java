package com.grandmasfood.v1.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse (
        UUID productUUID,
        String fantasyName,
        String category,
        String description,
        BigDecimal price,
        boolean available
) {
}
