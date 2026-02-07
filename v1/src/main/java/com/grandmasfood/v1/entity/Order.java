package com.grandmasfood.v1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID orderUUID;

    @Column(nullable = false)
    private LocalDateTime orderCreatedAt;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "TEXT", length = 511)
    private String additionalInfo;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva = BigDecimal.valueOf(1.19);
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @Column(nullable = false)
    private boolean delivered = false;
    @Column(nullable = false)
    private LocalDateTime deliveryDate = null;

    @PrePersist
    private void generateUUID(){
        if (orderUUID == null){
            orderUUID = UUID.randomUUID();
        }
    }
}
