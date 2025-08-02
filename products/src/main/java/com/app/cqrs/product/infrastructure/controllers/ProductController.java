package com.app.cqrs.product.infrastructure.controllers;

import org.springframework.web.bind.annotation.*;

import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping
    public String createProduct(@RequestBody CreateProductDto createProductDto) {
        return "Product created successfully!";
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
