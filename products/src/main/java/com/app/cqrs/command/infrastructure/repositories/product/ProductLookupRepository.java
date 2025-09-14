package com.app.cqrs.command.infrastructure.repositories.product;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.ProductLookup;
import com.app.cqrs.command.domain.ports.products.IProductLookupRepository;
import com.app.cqrs.command.infrastructure.entities.ProductLookupEntity;

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

    @Override
    public ProductLookup save(String id, String title) {
        var entity = new ProductLookupEntity(id, title);

        var savedEntity = productLookupJpa.save(entity);

        return new ProductLookup(savedEntity.getId(), savedEntity.getTitle());
    }

}
