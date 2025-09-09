package com.app.cqrs.command.infrastructure.gateways;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.command.domain.ports.IOrderCommandPort;
import com.app.cqrs.shared.domain.commands.ReservedCommandEvent;

@Component
public class OrderCommandGateway implements IOrderCommandPort {

    private final CommandGateway commandGateway;

    public OrderCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public Order createOrder(CreateOrderCommand order) {
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public void sendReservation(ReservedCommandEvent event, CommandCallback<ReservedCommandEvent, Object> callback) {
        this.commandGateway.send(event, callback);
    }

}
