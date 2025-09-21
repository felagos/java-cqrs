package com.app.cqrs.shared.domain.events.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReservedEvent {

    private String productId;
    private int quantity;
    private String orderId;
    private String userId;

}