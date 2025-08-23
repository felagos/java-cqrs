package com.app.cqrs.query.domain;

import java.math.BigDecimal;

public record ProductFilter (String title, BigDecimal minPrice, BigDecimal maxPrice) {

    public boolean isEmpty() {
        return (this.title == null || this.title.isBlank()) &&
               (this.minPrice == null) &&
               (this.maxPrice == null);
    }

}
