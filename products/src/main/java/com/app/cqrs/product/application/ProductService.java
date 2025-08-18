package com.app.cqrs.product.application;

import org.springframework.stereotype.Service;

import com.app.cqrs.product.domain.commands.CreateProductCommand;
import com.app.cqrs.product.domain.exceptions.ExistProductException;
import com.app.cqrs.product.domain.ports.IProductRepository;
import com.app.cqrs.product.domain.ports.ProductCommandGatewayPort;

@Service
public class ProductService {

    private final ProductCommandGatewayPort productCommandGateway;
    private final IProductRepository productRepository;

    public ProductService(ProductCommandGatewayPort productCommandGateway, IProductRepository productRepository) {
        this.productCommandGateway = productCommandGateway;
        this.productRepository = productRepository;
    }
    
    /**
     * Create a product using the command gateway.
     * @param product the product to create
     * @return the response from the command execution
     */
    public String createProduct(CreateProductCommand product) {
        var existProduct = this.productRepository.existsProductById(product.getProductId());

        if (existProduct) {
            throw new ExistProductException("Product with ID " + product.getProductId() + " already exists.");
        }

        return productCommandGateway.createProduct(product);
    }
}
