package com.app.cqrs.product.application;

import org.springframework.stereotype.Service;

import com.app.cqrs.product.domain.commands.CreateProductCommand;
import com.app.cqrs.product.domain.ports.ProductCommandGatewayPort;

@Service
public class ProductService {

    private final ProductCommandGatewayPort productCommandGateway;

    public ProductService(ProductCommandGatewayPort productCommandGateway) {
        this.productCommandGateway = productCommandGateway;
    }
    
    /**
     * Create a product using the command gateway.
     * @param product the product to create
     * @return the response from the command execution
     */
    public String createProduct(CreateProductCommand product) {
        return productCommandGateway.createProduct(product);
    }
}
