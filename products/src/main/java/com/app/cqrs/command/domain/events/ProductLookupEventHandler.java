package com.app.cqrs.command.domain.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.IProductLookupRepository;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {

    private final IProductLookupRepository productLookupRepository;

    public ProductLookupEventHandler(IProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        productLookupRepository.save(event.getProductId(), event.getTitle());
    }

}
