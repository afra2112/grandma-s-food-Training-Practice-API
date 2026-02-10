package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.CategoryRequest;
import com.grandmasfood.v1.dto.CategoryResponse;
import com.grandmasfood.v1.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    Category getCategoryById(Long categoryId);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryByIdDTO(Long categoryId);

    List<CategoryResponse> getAllCategories();
}
