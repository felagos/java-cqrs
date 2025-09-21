package com.app.cqrs.command.infrastructure.controllers;

import org.springframework.web.bind.annotation.*;

import com.app.cqrs.command.application.ProductCommandService;
import com.app.cqrs.command.infrastructure.dtos.CreateProductDto;
import com.app.cqrs.command.infrastructure.mappers.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private final ProductMapper productMapper;
    private final ProductCommandService productService;

    public ProductCommandController(
            ProductMapper productMapper,
            ProductCommandService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        var product = productMapper.toDomain(createProductDto);
        return this.productService.createProduct(product);
    }
    
}
