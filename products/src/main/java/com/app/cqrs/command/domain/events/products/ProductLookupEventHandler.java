package com.app.cqrs.command.domain.events.products;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.products.IProductLookupRepository;
import com.app.cqrs.shared.constants.ProcessGroups;

@Component
@ProcessingGroup(ProcessGroups.PRODUCT_GROUP)
public class ProductLookupEventHandler {

    private final IProductLookupRepository productLookupRepository;

    public ProductLookupEventHandler(IProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @EventHandler
    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        productLookupRepository.save(event.getProductId(), event.getTitle());
    }

    @ResetHandler
    public void reset() {
        this.productLookupRepository.deleteAllProducts();
    }

}
