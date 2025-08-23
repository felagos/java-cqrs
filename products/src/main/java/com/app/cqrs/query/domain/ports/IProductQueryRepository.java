package com.app.cqrs.query.domain.ports;

import java.util.List;
import com.app.cqrs.query.domain.Product;


public interface IProductQueryRepository {

    public List<Product> findAll();

}
