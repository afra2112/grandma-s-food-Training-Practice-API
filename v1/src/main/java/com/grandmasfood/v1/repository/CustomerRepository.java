package com.grandmasfood.v1.repository;

import com.grandmasfood.v1.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByDocumentAndDeletedFalse(String document);
    boolean existsByDocumentAndDeletedFalse(String document);
}
