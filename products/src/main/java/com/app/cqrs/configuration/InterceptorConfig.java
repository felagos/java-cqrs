package com.app.cqrs.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import com.app.cqrs.command.infrastructure.interceptors.CreateProductCommandInterceptor;

@Configuration
public class InterceptorConfig {

    @Autowired
    public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
        context.getBean(CreateProductCommandInterceptor.class);
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

}
