package com.app.cqrs.query.domain.queries.product;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class FindProductQuery {

    private final String title;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

}
