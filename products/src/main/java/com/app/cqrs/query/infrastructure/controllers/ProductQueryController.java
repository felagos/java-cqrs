package com.app.cqrs.query.infrastructure.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    @GetMapping
    public String getProduct() {
        return "Product details";
    }

}
