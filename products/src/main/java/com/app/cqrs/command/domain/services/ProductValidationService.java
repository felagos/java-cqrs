package com.app.cqrs.command.domain.services;

import org.springframework.stereotype.Service;

import com.app.cqrs.command.domain.exceptions.ExistingProductException;
import com.app.cqrs.command.domain.ports.IProductCommandRepository;

@Service
public class ProductValidationService {

    private final IProductCommandRepository productRepository;

    public ProductValidationService(IProductCommandRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateProductIdOrTitle(String id, String title) {
        var existProduct = this.productRepository.existsProductByIdOrTitle(id, title);

        if (existProduct) {
            throw new ExistingProductException("Product with title " + title + " and id " + id + " already exists.");
        }
    }

}
