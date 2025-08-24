package com.app.cqrs.query.domain.strategies;

import java.util.List;
import com.app.cqrs.query.domain.Product;

public interface ProductQueryStrategy {

    public List<Product> getProducts();

}
