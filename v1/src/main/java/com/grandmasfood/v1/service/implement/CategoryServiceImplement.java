package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.config.mapper.CategoryMapper;
import com.grandmasfood.v1.dto.CategoryRequest;
import com.grandmasfood.v1.dto.CategoryResponse;
import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.exception.CategoryAlreadyHasDisplayOrder;
import com.grandmasfood.v1.exception.EntityAlreadyExistsException;
import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.repository.CategoryRepository;
import com.grandmasfood.v1.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (categoryRepository.existsByName(request.name())){
            throw new EntityAlreadyExistsException(Category.class.getSimpleName(), request.name());
        }

        if (categoryRepository.existsByDisplayOrder(request.position())){
            throw new CategoryAlreadyHasDisplayOrder(request.position());
        }

        return categoryMapper.toDtoResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    public CategoryResponse getCategoryByIdDTO(Long categoryId) {
        return categoryMapper.toDtoResponse(categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new EntityNotFoundException(Category.class.getSimpleName(), String.valueOf(categoryId))));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDtoResponse).toList();
    }
}
