package com.app.cqrs.command.infrastructure.repositories;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.ports.IProductLookupRepository;

@Repository
public class ProductLookupRepository implements IProductLookupRepository {

    private final ProductLookupJpa productLookupJpa;

    public ProductLookupRepository(ProductLookupJpa productLookupJpa) {
        this.productLookupJpa = productLookupJpa;
    }

    public boolean existsByIdOrTitle(String id, String title) {
        var product = productLookupJpa.findByIdOrTitle(id, title);
        return product.isPresent();
    }

}
