package com.app.cqrs.query.infrastructure.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cqrs.command.application.ProductQueryService;
import com.app.cqrs.query.infrastructure.dtos.ProductDto;
import com.app.cqrs.query.infrastructure.mappers.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private final ProductQueryService productQueryService;
    private final ProductMapper productMapper;

    public ProductQueryController(ProductQueryService productQueryService, ProductMapper productMapper) {
        this.productQueryService = productQueryService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDto> getProduct() {
        var response = this.productQueryService.getAllProducts();
        return this.productMapper.toDtoList(response);
    }

}
