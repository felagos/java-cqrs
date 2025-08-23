package com.app.cqrs.command.application;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ports.IProductQueryGateway;

@Service
public class ProductQueryService {

    private final IProductQueryGateway productQueryGateway;

    public ProductQueryService(IProductQueryGateway productQueryGateway) {
        this.productQueryGateway = productQueryGateway;
    }

    public List<Product> getAllProducts() {
        return this.productQueryGateway.findAll();
    }

}
