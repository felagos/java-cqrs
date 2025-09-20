package com.app.cqrs.command.domain.events.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CancelProductReservationEvent {

    private final String productId;
    private final String orderId;
    private final String userId;
    private final int quantity;
    private final String reason;

}
