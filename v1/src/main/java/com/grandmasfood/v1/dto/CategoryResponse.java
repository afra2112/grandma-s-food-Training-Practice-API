package com.grandmasfood.v1.dto;

public record CategoryResponse (
        Long categoryId,
        String name,
        Integer order,
        boolean deleted
) {
}
