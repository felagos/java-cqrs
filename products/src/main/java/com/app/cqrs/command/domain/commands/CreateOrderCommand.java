package com.app.cqrs.command.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.app.cqrs.command.domain.OrderStatus;
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
