package com.app.cqrs.command.domain.ports;

public interface IProductLookupRepository {

    boolean existsByIdOrTitle(String id, String title);

}
