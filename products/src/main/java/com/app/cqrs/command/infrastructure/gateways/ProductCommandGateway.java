package com.app.cqrs.command.infrastructure.gateways;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import com.app.cqrs.command.domain.commands.CreateProductCommand;
import com.app.cqrs.command.domain.ports.IProductCommandPort;
import com.app.cqrs.shared.domain.Product;


@Component
public class ProductCommandGateway implements IProductCommandPort {

    private final CommandGateway commandGateway;

    public ProductCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public Product createProduct(CreateProductCommand product) {
        var response = commandGateway.sendAndWait(product);

        var createdProduct = new Product(product.getProductId(), product.getTitle(), product.getPrice(), product.getQuantity());

        System.out.println("Created Product: " + response);

        return createdProduct;
    }
}