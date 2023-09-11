package com.ahmetaksunger.jpaspecifications.service.impl;

import com.ahmetaksunger.jpaspecifications.dto.ListProductRequest;
import com.ahmetaksunger.jpaspecifications.entity.Product;
import com.ahmetaksunger.jpaspecifications.repository.ProductRepository;
import com.ahmetaksunger.jpaspecifications.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts(ListProductRequest listProductRequest) {
        return productRepository.findAll(listProductRequest.toSpecification());
    }
}
