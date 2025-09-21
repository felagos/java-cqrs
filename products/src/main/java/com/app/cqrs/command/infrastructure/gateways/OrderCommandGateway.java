package com.app.cqrs.command.infrastructure.gateways;

import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.commands.orders.CreateOrderCommand;
import com.app.cqrs.shared.domain.ports.orders.IOrderCommandPort;

@Component
public class OrderCommandGateway implements IOrderCommandPort {

    private final CommandGateway commandGateway;

    public OrderCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public Order createOrder(CreateOrderCommand order) {
        String response = sendSync(order);

        return new Order(response, order.getProductId(), order.getUserId(), order.getQuantity(), order.getAddressId(),
                order.getOrderStatus());
    }

    @Override
    public <T> String sendSync(T command) {
        return this.commandGateway.sendAndWait(command);
    }

    @Override
    public <T> String sendSync(T command, long timeout, TimeUnit timeUnit) {
        return this.commandGateway.sendAndWait(command, timeout, timeUnit);
    }

    @Override
    public <T> void send(T command, CommandCallback<T, Object> callback) {
        this.commandGateway.send(command, callback);
    }

}
