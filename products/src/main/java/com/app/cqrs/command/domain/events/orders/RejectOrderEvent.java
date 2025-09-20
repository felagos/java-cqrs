package com.app.cqrs.command.domain.events.orders;

import com.app.cqrs.command.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RejectOrderEvent {

    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus = OrderStatus.REJECTED;

    @Override
    public String toString() {
        return "RejectOrderEvent [orderId=" + orderId + ", reason=" + reason + ", orderStatus=" + orderStatus + "]";
    }

}
