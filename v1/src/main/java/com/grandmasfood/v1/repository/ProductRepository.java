package com.grandmasfood.v1.repository;

import com.grandmasfood.v1.dto.MostOrLessSoldProductResponse;
import com.grandmasfood.v1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(UUID productId);

    boolean existsByName(String name);

    boolean existsByNameAndProductIdNot(String fantasyName, UUID productId);

    List<Product> findByNameContainingIgnoreCase(String q);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.sells = p.sells + :quantity WHERE p.productId = :id")
    int increaseSellsAtomic(@Param("id") UUID id, @Param("quantity") Integer quantity);

    @Query("SELECT new com.grandmasfood.v1.dto.MostOrLessSoldProductResponse(p.name, SUM(o.quantity), p.basePrice)  FROM Order o JOIN o.product p WHERE o.orderCreatedAt BETWEEN :date1 AND :date2 GROUP BY p.name, p.basePrice")
    List<MostOrLessSoldProductResponse> findProductsToReportMoreThan0Sells(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);
}
