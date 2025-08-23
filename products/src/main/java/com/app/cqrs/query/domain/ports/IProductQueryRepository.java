package com.app.cqrs.query.domain.ports;

import java.util.List;
import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ProductFilter;


public interface IProductQueryRepository {

    public List<Product> findAll();

    public List<Product> findByFilter(ProductFilter filter);

}
