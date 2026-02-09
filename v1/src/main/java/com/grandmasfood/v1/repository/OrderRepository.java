package com.grandmasfood.v1.repository;

import com.grandmasfood.v1.dto.MostOrLessSoldProductResponse;
import com.grandmasfood.v1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderUUIDAndDeletedFalse(UUID orderUUID);
}
