package com.app.cqrs.configuration;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Configuration;

import com.app.cqrs.command.infrastructure.exceptions.ProductErrorEventHandler;
import com.app.cqrs.shared.constansts.ProcessGroups;

@Configuration
public class ErrorEventHandlerConfig {

    private EventProcessingConfigurer config;

    public ErrorEventHandlerConfig(EventProcessingConfigurer config) {
        this.config = config;
    }

    public void configure() {
        this.config.registerListenerInvocationErrorHandler(
                ProcessGroups.PRODUCT_GROUP,
                _ -> new ProductErrorEventHandler());
    }

}
