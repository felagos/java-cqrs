package com.app.cqrs.command.infrastructure.gateways;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.command.domain.ports.IOrderCommandPort;

@Component
public class OrderCommandGateway implements IOrderCommandPort {

    @Override
    public Order createOrder(CreateOrderCommand order) {
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

}
