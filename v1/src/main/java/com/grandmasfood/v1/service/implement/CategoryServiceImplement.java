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
        return categoryRepository.findByCategoryIdAndDeletedFalse(categoryId).orElseThrow(
                () -> new EntityNotFoundException(Category.class.getSimpleName(), String.valueOf(categoryId))
        );
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        validateNonDuplicatesCreate(request);
        return categoryMapper.toDtoResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    public CategoryResponse getCategoryByIdDTO(Long categoryId) {
        return categoryMapper.toDtoResponse(categoryRepository.findByCategoryIdAndDeletedFalse(categoryId).orElseThrow(
                () -> handleOrElseThrow(String.valueOf(categoryId))));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findByDeletedFalse().stream()
                .map(categoryMapper::toDtoResponse).toList();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        validateNonDuplicatesUpdate(request, id);

        Category category = categoryRepository.findByCategoryIdAndDeletedFalse(id).orElseThrow(
                () -> handleOrElseThrow(String.valueOf(id))
        );
        return categoryMapper.toDtoResponse(categoryRepository.save(categoryMapper.toEntityUpdate(request, category)));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findByCategoryIdAndDeletedFalse(id).orElseThrow(
                () -> handleOrElseThrow(String.valueOf(id))
        );

        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategoriesOrderedByDisplayOrder() {
        return categoryRepository.getCategoriesWithProductsAndOrderedByDisplayOrder();
    }

    private void validateNonDuplicatesCreate(CategoryRequest request){
        if (categoryRepository.existsByNameOrDisplayOrderAndDeletedFalse(request.name(), request.position())){
            throw new EntityAlreadyExistsException(Category.class.getSimpleName(), request.name());
        }
    }

    private void validateNonDuplicatesUpdate(CategoryRequest request, Long id){
        if (categoryRepository.existsByNameAndDeletedFalseAndCategoryIdNot(request.name(), id)){
            throw new EntityAlreadyExistsException(Category.class.getSimpleName(), request.name());
        }
        if (categoryRepository.existsByDisplayOrderAndDeletedFalseAndCategoryIdNot(request.position(), id)){
            throw new CategoryAlreadyHasDisplayOrder(request.position());
        }
    }

    private EntityNotFoundException handleOrElseThrow(String identification){
        return new EntityNotFoundException(Category.class.getSimpleName(), identification);
    }
}
