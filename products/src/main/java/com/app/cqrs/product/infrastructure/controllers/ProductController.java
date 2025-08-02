package com.app.cqrs.product.infrastructure.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;
import com.app.cqrs.product.infrastructure.mappers.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductMapper productMapper;
    private final CommandGateway commandGateway;

    public ProductController(ProductMapper productMapper, CommandGateway commandGateway) {
        this.productMapper = productMapper;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        var product = productMapper.toDomain(createProductDto);
        String response = this.commandGateway.sendAndWait(product);
        return response;
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
