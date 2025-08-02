package com.app.cqrs.product.infrastructure.controllers;

import org.springframework.web.bind.annotation.*;

import com.app.cqrs.product.domain.Product;
import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;
import com.app.cqrs.product.infrastructure.gateways.CommandGatewayWrapper;
import com.app.cqrs.product.infrastructure.mappers.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductMapper productMapper;
    private final CommandGatewayWrapper<Product> commandGatewayWrapper;

    public ProductController(
            ProductMapper productMapper,
            CommandGatewayWrapper<Product> commandGatewayWrapper) {
        this.productMapper = productMapper;
        this.commandGatewayWrapper = commandGatewayWrapper;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        var product = productMapper.toDomain(createProductDto);
        var response = this.commandGatewayWrapper.<String>createCommand(product);
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
