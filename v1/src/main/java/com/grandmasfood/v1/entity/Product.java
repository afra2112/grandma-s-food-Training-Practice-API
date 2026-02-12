package com.grandmasfood.v1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private boolean available = false;

    @Column(nullable = false)
    private Integer sells = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId")
    private Category category;

    public Product(String name, String description, Category category, BigDecimal price, boolean available) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.basePrice = price;
        this.available = available;
    }
}
