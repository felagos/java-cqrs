package com.app.cqrs.command.application;

import org.springframework.stereotype.Service;
import com.app.cqrs.shared.domain.commands.orders.CreateOrderCommand;
import com.app.cqrs.shared.domain.ports.orders.IOrderCommandPort;

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
