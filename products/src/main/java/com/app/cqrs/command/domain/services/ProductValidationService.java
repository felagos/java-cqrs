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

    public void validateTitleUniqueness(String title) {
        var existProduct = this.productRepository.existsProductByTitle(title);

        if (existProduct) {
            throw new ExistingProductException("Product with title " + title + " already exists.");
        }
    }

}
