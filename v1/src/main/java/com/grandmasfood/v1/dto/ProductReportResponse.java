package com.grandmasfood.v1.dto;

import jakarta.annotation.Nullable;
import java.util.List;

public record ProductReportResponse(
        List<SingleProductToReportResponse> productsWithSells,
        String mostSoldProductName,
        String lessSoldProductName,
        @Nullable
        List<MostOrLessSoldProductResponse> mostSoldProducts,
        @Nullable
        List<MostOrLessSoldProductResponse> lessSoldProducts
) {
}
