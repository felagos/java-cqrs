package com.app.cqrs.configuration;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.app.cqrs.command.infrastructure.exceptions.ProductErrorEventHandler;

@Configuration
public class ErrorEventHandlerConfig {

    @Autowired
    public void configure(EventProcessingConfigurer config) {
        config.registerListenerInvocationErrorHandler(
                "product",
                conf -> new ProductErrorEventHandler());
    }

}
