package com.grandmasfood.v1.config.exception;

import com.grandmasfood.v1.config.enums.ErrorCodeEnum;

import java.time.Instant;
import java.util.Map;

public record SpringValidationErrorResponse(
        ErrorCodeEnum code,
        Instant timestamp,
        String description,
        String exception,
        Map<String, String> errors
) {
}
