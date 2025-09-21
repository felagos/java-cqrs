package com.app.cqrs.command.application;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.springframework.stereotype.Service;
import com.app.cqrs.shared.domain.commands.orders.CreateOrderCommand;
import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.ports.orders.IOrderCommandPort;
import com.app.cqrs.shared.domain.query.orders.FindOrderQuery;

@Service
public class OrderCommandService {

    private final IOrderCommandPort orderCommandPort;

    public OrderCommandService(IOrderCommandPort orderCommandPort) {
        this.orderCommandPort = orderCommandPort;
    }

    public Order createOrder(CreateOrderCommand command) {
        try (var querySubscription = this.orderCommandPort.subscriptionQuery(
                new FindOrderQuery(command.getOrderId()),
                ResponseTypes.instanceOf(Order.class), ResponseTypes.instanceOf(Order.class))) {

            this.orderCommandPort.createOrder(command);

            return querySubscription.updates().blockFirst();
        }
    }

}
