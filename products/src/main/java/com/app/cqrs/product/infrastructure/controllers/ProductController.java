package com.app.cqrs.product.infrastructure.controllers;

import org.springframework.web.bind.annotation.*;

import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;
import com.app.cqrs.product.infrastructure.mappers.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductMapper productMapper;

    public ProductController(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        return "Product created successfully with title: " + createProductDto.title();
    }

    @GetMapping
    public String getProduct() {
        return "Product details";
    }

    @PutMapping
    public String updateProduct() {
        return "Product updated successfully!";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Product deleted successfully!";
    }

}
