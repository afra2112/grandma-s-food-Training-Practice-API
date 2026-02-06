package com.grandmasfood.v1.repository;

import com.grandmasfood.v1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(UUID productId);

    boolean existsByName(String name);

    boolean existsByNameAndProductIdNot(String fantasyName, UUID productId);
}
