package com.ahmetaksunger.jpaspecifications.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

}
