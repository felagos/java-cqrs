package com.app.cqrs.command.application;

import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import com.app.cqrs.command.domain.commands.CreateProductCommand;
import com.app.cqrs.command.domain.exceptions.ExistingProductException;
import com.app.cqrs.command.domain.ports.IProductCommandPort;
import com.app.cqrs.command.domain.services.ProductValidationService;

@Service
public class ProductCommandService {

    private static final Logger logger = Logger.getLogger(ProductCommandService.class.getName());

    private final IProductCommandPort productCommandGateway;
    private final ProductValidationService productValidationService;

    public ProductCommandService(
            IProductCommandPort productCommandGateway,
            ProductValidationService productValidationService) {
        this.productCommandGateway = productCommandGateway;
        this.productValidationService = productValidationService;
    }

    /**
     * Creates a product by sending a CreateProductCommand to the command gateway.
     *
     * <p>
     * This method first checks the read-model repository to ensure a product with
     * the
     * same productId does not already exist. If a product with the same id exists,
     * an
     * {@link ExistingProductException} is thrown. Otherwise the command is
     * forwarded to
     * {@link IProductCommandPort#createProduct(CreateProductCommand)} for
     * execution.
     * </p>
     *
     * @param product the create command containing the product id and payload; must
     *                not be null
     * @return a string result from the command execution (implementation-specific)
     * @throws ExistingProductException if a product with the same id already exists
     */
    public String createProduct(CreateProductCommand product) {
        this.productValidationService.validateTitleUniqueness(product.getTitle());

        var productCreated = productCommandGateway.createProduct(product);

        logger.info("Product created with id: " + productCreated.getId());

        return productCreated.getId();
    }
}
