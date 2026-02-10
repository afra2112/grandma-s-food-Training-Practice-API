package com.grandmasfood.v1.config.mapper;

import com.grandmasfood.v1.dto.MostOrLessSoldProductResponse;
import com.grandmasfood.v1.dto.ProductRequest;
import com.grandmasfood.v1.dto.ProductResponse;
import com.grandmasfood.v1.entity.Product;
import com.grandmasfood.v1.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {

    private final CategoryService categoryService;

    public ProductResponse toDto(Product entity){
        return new ProductResponse(
                entity.getProductId(),
                entity.getName(),
                entity.getCategory().getName(),
                entity.getDescription(),
                entity.getBasePrice(),
                entity.isAvailable()
        );
    }

    public MostOrLessSoldProductResponse toMostOrLessProductResponse(Product entity){
        return new MostOrLessSoldProductResponse(
                entity.getName(),
                Long.valueOf(entity.getSells()),
                entity.getBasePrice()
        );
    }

    public Product toEntity(ProductRequest request){
        return new Product(
                request.fantasyName().toUpperCase(),
                request.description(),
                categoryService.getCategoryById(request.categoryId()),
                request.price(),
                request.available()
        );
    }
}
