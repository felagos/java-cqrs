package com.app.cqrs.query.infrastructure.dtos;

import java.math.BigDecimal;

public record ProductDto(String id, String title, BigDecimal price, Integer quantity) {

}
