package com.app.cqrs.shared.domain.commands.orders;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.app.cqrs.shared.domain.orders.OrderStatus;
import lombok.Value;

@Value
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    String orderId;
    String userId;
    String productId;
    int quantity;
    String addressId;
    OrderStatus orderStatus;

}