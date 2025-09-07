package com.app.cqrs.command.domain.ports;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;

public interface IOrderCommandPort {

    Order createOrder(CreateOrderCommand order);

}
