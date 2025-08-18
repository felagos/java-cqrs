package com.app.cqrs.product.infrastructure.gateways;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import com.app.cqrs.product.domain.commands.CreateProductCommand;
import com.app.cqrs.product.domain.ports.IProductCommandPort;


@Component
public class ProductCommandGateway implements IProductCommandPort {

    private final CommandGateway commandGateway;

    public ProductCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public <R> R createProduct(CreateProductCommand product) {
        return commandGateway.sendAndWait(product);
    }
}