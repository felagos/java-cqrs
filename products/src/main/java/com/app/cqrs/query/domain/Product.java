package com.app.cqrs.query.domain;

import java.math.BigDecimal;

public record Product(String id, String title, BigDecimal price, Integer quantity) {

}
