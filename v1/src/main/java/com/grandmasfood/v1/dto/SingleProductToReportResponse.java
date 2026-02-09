package com.grandmasfood.v1.dto;

import java.math.BigDecimal;

public record SingleProductToReportResponse(
        Integer quantity,
        BigDecimal grossIncome
) {
}
