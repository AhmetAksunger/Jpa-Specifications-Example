package com.ahmetaksunger.jpaspecifications.service;

import com.ahmetaksunger.jpaspecifications.dto.ListProductRequest;
import com.ahmetaksunger.jpaspecifications.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ListProductRequest listProductRequest);
}
