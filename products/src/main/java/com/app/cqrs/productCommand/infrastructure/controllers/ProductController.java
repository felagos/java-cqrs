package com.app.cqrs.productcommand.infrastructure.controllers;

import org.springframework.web.bind.annotation.*;

import com.app.cqrs.productcommand.application.ProductService;
import com.app.cqrs.productcommand.infrastructure.dtos.CreateProductDto;
import com.app.cqrs.productcommand.infrastructure.mappers.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductController(
            ProductMapper productMapper,
            ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        var product = productMapper.toDomain(createProductDto);
        return this.productService.createProduct(product);
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
