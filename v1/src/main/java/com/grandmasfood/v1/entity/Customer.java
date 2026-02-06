package com.grandmasfood.v1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 20, unique = true)
    private String document;

    @Column(nullable = false)
    private String nameAndSurname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, length = 500)
    private String shippingAddress;

    private boolean deleted = false;

    @OneToMany(
            mappedBy = "customer",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Order> orders = new HashSet<>();

    public Customer(String document, String nameAndSurname, String email, String phoneNumber, String shippingAddress) {
        this.document = document;
        this.nameAndSurname = nameAndSurname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
    }
}
