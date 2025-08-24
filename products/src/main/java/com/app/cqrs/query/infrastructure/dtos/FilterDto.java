package com.app.cqrs.query.infrastructure.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.PositiveOrZero;

public class FilterDto {

    private String title;

    @PositiveOrZero(message = "Price must be zero or positive")
    private BigDecimal minPrice;

    @PositiveOrZero(message = "Price must be zero or positive")
    private BigDecimal maxPrice;

    public FilterDto() {
    }

    public FilterDto(String title, BigDecimal minPrice, BigDecimal maxPrice) {
        this.title = title;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
