package com.grandmasfood.v1.repository;

import com.grandmasfood.v1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryIdAndDeletedFalse(Long id);

    boolean existsByNameOrDisplayOrderAndDeletedFalse(String name, Integer displayOrder);

    boolean existsByNameAndDeletedFalseAndCategoryIdNot(String name, Long categoryId);

    boolean existsByDisplayOrderAndDeletedFalseAndCategoryIdNot(Integer displayOrder, Long categoryId);

    List<Category> findByDeletedFalse();
}
