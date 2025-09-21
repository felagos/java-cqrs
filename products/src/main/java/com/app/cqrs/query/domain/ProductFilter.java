package com.app.cqrs.query.domain;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class ProductFilter {
    String title;
    BigDecimal minPrice;
    BigDecimal maxPrice;

    public boolean hasTitle() {
        return this.title != null && !this.title.isBlank();
    }

    public boolean hasMinPrice() {
        return this.minPrice != null && this.minPrice.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasMaxPrice() {
        return this.maxPrice != null && this.maxPrice.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isEmpty() {
        return !hasTitle() && !hasMinPrice() && !hasMaxPrice();
    }
}
