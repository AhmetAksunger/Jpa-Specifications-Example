package com.ahmetaksunger.jpaspecifications.controller;

import com.ahmetaksunger.jpaspecifications.dto.ListProductRequest;
import com.ahmetaksunger.jpaspecifications.entity.Product;
import com.ahmetaksunger.jpaspecifications.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestBody ListProductRequest listProductRequest) {
        return ResponseEntity.ok(productService.getProducts(listProductRequest));
    }
}
