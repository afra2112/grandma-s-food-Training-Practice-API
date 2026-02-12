package com.grandmasfood.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoryRequest (
        @NotBlank
        String name,

        @Positive
        @NotNull
        Integer position
) {
}
