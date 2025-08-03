package com.app.cqrs.product.infrastructure.gateways;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import com.app.cqrs.product.domain.Product;
import com.app.cqrs.product.domain.ports.ProductCommandGatewayPort;


@Component
public class ProductCommandGateway implements ProductCommandGatewayPort {

    private final CommandGateway commandGateway;

    public ProductCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public <R> R createProduct(Product product) {
        return commandGateway.sendAndWait(product);
    }
}