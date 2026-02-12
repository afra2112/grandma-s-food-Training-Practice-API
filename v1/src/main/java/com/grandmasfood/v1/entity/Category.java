package com.grandmasfood.v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer displayOrder;

    private boolean deleted = false;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(String name, Integer order){
        this.name = name;
        this.displayOrder = order;
    }
}
