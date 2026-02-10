package com.grandmasfood.v1.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        String fantasyName,

        @NotNull
        Long categoryId,

        @NotBlank
        String description,

        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @NotNull
        boolean available
) {
}
