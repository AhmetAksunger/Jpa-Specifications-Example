package com.ahmetaksunger.jpaspecifications.dto;

import org.springframework.data.jpa.domain.Specification;

public interface Filterable<T> {

    /**
     * Converts requests into specification
     * @return {@link Specification<T>}
     */
    Specification<T> toSpecification();
}
