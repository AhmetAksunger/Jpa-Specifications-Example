package com.ahmetaksunger.jpaspecifications.repository;

import com.ahmetaksunger.jpaspecifications.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
