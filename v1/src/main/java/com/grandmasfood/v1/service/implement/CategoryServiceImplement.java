package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.config.mapper.CategoryMapper;
import com.grandmasfood.v1.dto.CategoryRequest;
import com.grandmasfood.v1.dto.CategoryResponse;
import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.repository.CategoryRepository;
import com.grandmasfood.v1.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImplement implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new EntityNotFoundException(Category.class.getSimpleName(), String.valueOf(categoryId))
        );
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        return categoryMapper.toDtoResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }
}
