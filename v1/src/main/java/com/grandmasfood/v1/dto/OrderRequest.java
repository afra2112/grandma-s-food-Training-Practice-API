package com.grandmasfood.v1.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRequest (

        @NotNull
        LocalDateTime orderDateAndTime,

        @NotBlank
        @Pattern(
                regexp = "^(CC|TI|CE|P)-\\d{1,17}$",
                message = "The customer document must be like (document type-number) ex: 'CC-1234567890'"
        )
        @Size(max = 20)
        String customerDocument,

        @NotNull
        UUID productId,

        @Positive
        @Size(max = 99)
        int quantity,

        @Null
        @Size(max = 511)
        String additionalInfo
) {
}
