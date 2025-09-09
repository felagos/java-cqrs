package com.app.cqrs.command.domain.ports;

import org.axonframework.commandhandling.CommandCallback;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.shared.domain.commands.ReservedCommandEvent;

public interface IOrderCommandPort {

    Order createOrder(CreateOrderCommand order);
    void sendReservation(ReservedCommandEvent event, CommandCallback<ReservedCommandEvent, Object> callback);

}
