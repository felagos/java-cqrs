package com.app.cqrs.query.domain.queries;

import com.app.cqrs.query.domain.ProductFilter;

public class FilterProductQuery {

    private final ProductFilter filter;

    public FilterProductQuery(ProductFilter filter) {
        this.filter = filter;
    }

    public ProductFilter getFilter() {
        return filter;
    }

}
