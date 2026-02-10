package com.grandmasfood.v1.config.mapper;

import com.grandmasfood.v1.dto.CategoryRequest;
import com.grandmasfood.v1.dto.CategoryResponse;
import com.grandmasfood.v1.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toDtoResponse(Category category){
        return new CategoryResponse(
                category.getCategoryId(),
                category.getName(),
                category.getDisplayOrder(),
                category.isDeleted()
        );
    }

    public Category toEntity(CategoryRequest request){
        return new Category(
                request.name(),
                request.position()
        );
    }
}
