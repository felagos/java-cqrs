package com.app.cqrs.shared.domain.events.orders;

import com.app.cqrs.shared.domain.orders.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RejectOrderEvent {

    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus = OrderStatus.REJECTED;

}