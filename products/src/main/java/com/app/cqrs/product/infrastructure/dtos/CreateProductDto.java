package com.app.cqrs.product.infrastructure.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

public class CreateProductDto {

    @NotEmpty(message = "Title cannot be empty")
    private final String title;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private final BigDecimal price;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private final Integer quantity;

    public CreateProductDto(String title, BigDecimal price, Integer quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CreateProductDto that = (CreateProductDto) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, quantity);
    }

    @Override
    public String toString() {
        return "CreateProductDto{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}