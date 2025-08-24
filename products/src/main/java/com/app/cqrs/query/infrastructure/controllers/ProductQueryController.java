package com.app.cqrs.query.infrastructure.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cqrs.query.application.ProductQueryService;
import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.query.infrastructure.dtos.FilterDto;
import com.app.cqrs.query.infrastructure.dtos.ProductDto;
import com.app.cqrs.query.infrastructure.mappers.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private final ProductQueryService productQueryService;
    private final ProductMapper productMapper;

    public ProductQueryController(ProductQueryService productQueryService, ProductMapper productMapper) {
        this.productQueryService = productQueryService;
        this.productMapper = productMapper;
    }

    @GetMapping()
    public List<ProductDto> getProducts(@Valid @ModelAttribute FilterDto filterDto) {
        var filter = new ProductFilter(filterDto.getTitle(), filterDto.getMinPrice(), filterDto.getMaxPrice());
        var response = this.productQueryService.getAllProducts(filter);
        return this.productMapper.toDtoList(response);
    }

}
