package com.app.cqrs.query.infrastructure.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.cqrs.query.application.ProductQueryService;
import com.app.cqrs.query.domain.ProductFilter;
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

    @GetMapping()
    public List<ProductDto> getProducts(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice) {
        var filter = new ProductFilter(title, minPrice, maxPrice);
        var response = this.productQueryService.getAllProducts(filter);
        return this.productMapper.toDtoList(response);
    }

}
