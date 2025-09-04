package com.app.cqrs.query.domain.ports;

import java.util.List;

import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.shared.domain.Product;


public interface IProductQueryRepository {

    public List<Product> findAll();

    public List<Product> findByFilter(ProductFilter filter);

}
