package com.app.cqrs.productcommand.application;

import org.springframework.stereotype.Service;

import com.app.cqrs.productcommand.domain.commands.CreateProductCommand;
import com.app.cqrs.productcommand.domain.exceptions.ExistingProductException;
import com.app.cqrs.productcommand.domain.ports.IProductCommandPort;
import com.app.cqrs.productcommand.domain.ports.IProductRepository;

@Service
public class ProductService {

    private final IProductCommandPort productCommandGateway;
    private final IProductRepository productRepository;

    public ProductService(IProductCommandPort productCommandGateway, IProductRepository productRepository) {
        this.productCommandGateway = productCommandGateway;
        this.productRepository = productRepository;
    }
    
    /**
    * Creates a product by sending a CreateProductCommand to the command gateway.
    *
    * <p>This method first checks the read-model repository to ensure a product with the
    * same productId does not already exist. If a product with the same id exists, an
    * {@link ExistingProductException} is thrown. Otherwise the command is forwarded to
    * {@link IProductCommandPort#createProduct(CreateProductCommand)} for execution.</p>
    *
    * @param product the create command containing the product id and payload; must not be null
    * @return a string result from the command execution (implementation-specific)
    * @throws ExistingProductException if a product with the same id already exists
    */
    public String createProduct(CreateProductCommand product) {
        var existProduct = this.productRepository.existsProductById(product.getProductId());

        if (existProduct) {
            throw new ExistingProductException("Product with ID " + product.getProductId() + " already exists.");
        }

        return productCommandGateway.createProduct(product);
    }
}
