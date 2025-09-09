package com.app.cqrs.command.domain.ports;

import org.axonframework.commandhandling.CommandCallback;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;

public interface IOrderCommandPort {

    Order createOrder(CreateOrderCommand order);
    void sendReservation(ReserveProductCommand event, CommandCallback<ReserveProductCommand, Object> callback);

}
