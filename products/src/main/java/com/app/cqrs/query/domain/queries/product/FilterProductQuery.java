package com.app.cqrs.query.domain.queries.product;

import com.app.cqrs.query.domain.ProductFilter;
import lombok.Value;

@Value
public class FilterProductQuery {
    ProductFilter filter;
}
