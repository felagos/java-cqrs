package com.app.cqrs.query.domain.ports;

import java.util.List;

import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ProductFilter;

public interface IProductQueryGateway {

    List<Product> findAll();

    List<Product> findByFilter(ProductFilter filter);

}
