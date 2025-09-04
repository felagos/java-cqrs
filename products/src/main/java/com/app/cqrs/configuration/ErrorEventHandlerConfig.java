package com.app.cqrs.configuration;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.app.cqrs.command.infrastructure.exceptions.ProductErrorEventHandler;

@Configuration
public class ErrorEventHandlerConfig {

    private EventProcessingConfigurer config;

    public ErrorEventHandlerConfig(EventProcessingConfigurer config) {
        this.config = config;
    }

    public void configure() {
        this.config.registerListenerInvocationErrorHandler(
                "product-group",
                _ -> new ProductErrorEventHandler());
    }

}
