package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.CategoryRequest;
import com.grandmasfood.v1.dto.CategoryResponse;
import com.grandmasfood.v1.entity.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    Category getCategoryById(Long categoryId);

    CategoryResponse createCategory(CategoryRequest request);
}
