package com.app.cqrs.configuration;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.app.cqrs.command.infrastructure.exceptions.ProductErrorEventHandler;
import com.app.cqrs.shared.constants.ProcessGroups;

@Configuration
public class ErrorEventHandlerConfig {

    @Autowired
    public void configure(EventProcessingConfigurer config) {
        System.out.println("Configuring event handler error");
        config.registerListenerInvocationErrorHandler(
                ProcessGroups.PRODUCT_GROUP,
                _ -> new ProductErrorEventHandler());
    }

}
