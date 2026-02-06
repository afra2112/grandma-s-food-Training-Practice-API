package com.grandmasfood.v1.config.exception;

import com.grandmasfood.v1.config.enums.ErrorCodeEnum;

import java.time.Instant;

public record ApiErrorResponse(
        ErrorCodeEnum code,
        Instant timestamp,
        String description,
        String exception
) {
}
