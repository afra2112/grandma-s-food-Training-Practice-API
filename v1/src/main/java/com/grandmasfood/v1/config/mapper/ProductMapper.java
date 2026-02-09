package com.grandmasfood.v1.config.mapper;

import com.grandmasfood.v1.dto.ProductRequest;
import com.grandmasfood.v1.dto.ProductResponse;
import com.grandmasfood.v1.dto.SingleProductToReportResponse;
import com.grandmasfood.v1.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toDto(Product entity){
        return new ProductResponse(
                entity.getProductId(),
                entity.getName(),
                entity.getCategory(),
                entity.getDescription(),
                entity.getBasePrice(),
                entity.isAvailable()
        );
    }

    public SingleProductToReportResponse toSingleProductReportResponse(Product entity){
        return new SingleProductToReportResponse(
                entity.getSells(),
                entity.getBasePrice()
        );
    }

    public Product toEntity(ProductRequest request){
        return new Product(
                request.fantasyName().toUpperCase(),
                request.category(),
                request.description(),
                request.price(),
                request.available()
        );
    }
}
