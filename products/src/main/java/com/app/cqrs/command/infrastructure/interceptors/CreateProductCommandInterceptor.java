package com.app.cqrs.command.infrastructure.interceptors;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;
import com.app.cqrs.command.domain.commands.CreateProductCommand;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger logger = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (_, message) -> {
            if (CreateProductCommand.class.equals(message.getPayloadType())) {
                CreateProductCommand command = (CreateProductCommand) message.getPayload();

                if (command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    logger.warn("Invalid product price: " + command.getPrice());
                }

                logger.info("Creating product: " + command.getTitle() + " with price: " + command.getPrice());

                return message;
            }
            return message;
        };
    }

}
