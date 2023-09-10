package com.ahmetaksunger.jpaspecifications.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity{

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

}
