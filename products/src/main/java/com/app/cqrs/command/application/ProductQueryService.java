package com.app.cqrs.command.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.cqrs.query.domain.Product;

@Service
public class ProductQueryService {

    public List<Product> getAllProducts() {
        return new ArrayList<>();
    }

}
