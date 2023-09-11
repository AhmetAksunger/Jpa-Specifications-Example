package com.ahmetaksunger.jpaspecifications.repository;

import com.ahmetaksunger.jpaspecifications.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
