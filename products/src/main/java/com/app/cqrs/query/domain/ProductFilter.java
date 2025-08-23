package com.app.cqrs.query.domain;

import java.math.BigDecimal;

public class ProductFilter {

    private final String title;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;

    public ProductFilter(String title, BigDecimal minPrice, BigDecimal maxPrice) {
        this.title = title;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    // JavaBean getters
    public String getTitle() {
        return title;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public boolean isEmpty() {
        return (this.title == null || this.title.isBlank()) &&
                (this.minPrice == null) &&
                (this.maxPrice == null);
    }

}
