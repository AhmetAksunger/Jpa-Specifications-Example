package com.ahmetaksunger.jpaspecifications.specification;

import com.ahmetaksunger.jpaspecifications.entity.Category;
import com.ahmetaksunger.jpaspecifications.entity.Product;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class ProductSpecification {

    private ProductSpecification() {
    }


    // #### EQUALITY
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Product> createdAt(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createdAt"), date);
    }

    public static Specification<Product> hasNoName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("name"), name);
    }
    // ####

    // #### STRINGS
    public static Specification<Product> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
    // ###

    // ### COMPARISONS
    public static Specification<Product> hasPriceGreaterThanOrEqualTo(BigDecimal price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> hasPriceLessThanOrEqualTo(BigDecimal price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("name"), price);
    }

    public static Specification<Product> hasPriceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), min, max);
    }

    public static Specification<Product> createdBeforeThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("createdAt"), date);
    }

    public static Specification<Product> createdAfterThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("createdAt"), date);
    }

    public static Specification<Product> updatedBeforeThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("updatedAt"), date);
    }

    public static Specification<Product> updatedAfterThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("updatedAt"), date);
    }
    // ###

    // ### JOINS
    public static Specification<Product> hasCategoryNameLike(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");

            return criteriaBuilder.like(categoryJoin.get("name"), categoryName);
        };
    }

    public static Specification<Product> hasCategoryIds(Collection<Long> categoryIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");

            return categoryJoin.get("id").in(categoryIds);
        };
    }
    // ###

    // ### BOOLEANS
    public static Specification<Product> search(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("companyName"), "%" + keyword + "%")
                );
    }
    // ###
}
