package com.app.cqrs.shared.domain;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class Product {
    String id;
    String title;
    BigDecimal price;
    Integer quantity;
}
