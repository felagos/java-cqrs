package com.app.cqrs.command.application;

import org.springframework.stereotype.Service;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.command.domain.ports.orders.IOrderCommandPort;

@Service
public class OrderCommandService {

    private final IOrderCommandPort orderCommandPort;

    public OrderCommandService(IOrderCommandPort orderCommandPort) {
        this.orderCommandPort = orderCommandPort;
    }

    public String createOrder(CreateOrderCommand command) {
        var order = this.orderCommandPort.createOrder(command);
        return order.getOrderId();
    }

}
