package com.app.cqrs.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping
    public String createProduct() {
        return "Product created successfully!";
    }

    @GetMapping
    public String getProduct() {
        return "Product details";
    }

    @PostMapping
    public String updateProduct() {
        return "Product updated successfully!";
    }

    @PostMapping
    public String deleteProduct() {
        return "Product deleted successfully!";
    }

}
