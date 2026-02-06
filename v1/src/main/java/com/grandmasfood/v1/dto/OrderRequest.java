package com.grandmasfood.v1.dto;

import com.grandmasfood.v1.config.customBeans.Document;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRequest (

        @Document
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
