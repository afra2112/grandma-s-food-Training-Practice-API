package com.grandmasfood.v1.dto;

import com.grandmasfood.v1.config.customBeans.Document;
import jakarta.validation.constraints.*;
import java.util.UUID;

public record OrderRequest (

        @Document
        String customerDocument,

        @NotNull
        UUID productId,

        @NotNull
        @Positive
        @Max(99)
        Integer quantity,

        @Size(max = 511)
        String additionalInfo
) {
}
