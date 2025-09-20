package com.app.cqrs.command.domain.events.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CancelProductReservationEvent {

    private final String productId;
    private final String orderId;
    private final String userId;
    private final int quantity;
    private final String reason;

    @Override
    public String toString() {
        return "CancelProductReservationEvent [productId=" + productId + ", orderId=" + orderId + ", userId=" + userId
                + ", quantity=" + quantity + ", reason=" + reason + "]";
    }

}
