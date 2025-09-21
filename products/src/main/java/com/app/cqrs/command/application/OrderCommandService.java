package com.app.cqrs.command.application;

import java.time.Duration;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.app.cqrs.shared.domain.commands.orders.CreateOrderCommand;
import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.ports.orders.IOrderCommandPort;
import com.app.cqrs.shared.domain.query.orders.FindOrderQuery;

@Service
public class OrderCommandService {

    private static final Logger logger = LoggerFactory.getLogger(OrderCommandService.class);

    private final IOrderCommandPort orderCommandPort;

    public OrderCommandService(IOrderCommandPort orderCommandPort) {
        this.orderCommandPort = orderCommandPort;
    }

    public Order createOrder(CreateOrderCommand command) {
        try (var querySubscription = this.orderCommandPort.subscriptionQuery(
                new FindOrderQuery(command.getOrderId()),
                ResponseTypes.instanceOf(Order.class), ResponseTypes.instanceOf(Order.class))) {

            logger.info("Created subscription query for order: {}", command.getOrderId());

            this.orderCommandPort.createOrder(command);

            logger.info("CreateOrderCommand sent: {}", command);

            // Add timeout to prevent infinite blocking - wait max 5 seconds for initial response
            var order = querySubscription.updates()
                    .timeout(Duration.ofSeconds(20))
                    .blockFirst();

            logger.info("Order created: {}", order);

            return order;
        }
    }

}
