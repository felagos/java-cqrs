package com.app.cqrs.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import com.app.cqrs.command.infrastructure.interceptors.CreateProductCommandInterceptor;

@Configuration
public class InterceptorConfig {

    private final ApplicationContext context;
    private final CommandBus commandBus;


    public InterceptorConfig(ApplicationContext context, CommandBus commandBus) {
        this.context = context;
        this.commandBus = commandBus;
    }

    @Autowired
    public void registerCreateProductCommandInterceptor() {
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

}
