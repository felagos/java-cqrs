package com.app.cqrs.query.domain.queries.order;

import lombok.Value;

@Value
public class FindOrderQuery {

    private final String orderId;

}
