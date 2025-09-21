package com.app.cqrs.shared.domain.query.orders;

import lombok.Value;

@Value
public class FindOrderQuery {

    private final String orderId;

}