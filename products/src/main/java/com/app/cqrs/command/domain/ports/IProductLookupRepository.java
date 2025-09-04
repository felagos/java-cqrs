package com.app.cqrs.command.domain.ports;

import com.app.cqrs.command.domain.ProductLookup;

public interface IProductLookupRepository {

    boolean existsByIdOrTitle(String id, String title);

    ProductLookup save(String id, String title);

}
