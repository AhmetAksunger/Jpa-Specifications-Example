package com.ahmetaksunger.jpaspecifications.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "PRODUCTS")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "STOCK_QUANTITY")
    private Integer stockQuantity;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @OneToMany
    private Set<Category> categories;

}
