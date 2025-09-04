package com.app.cqrs.command.infrastructure.interceptors;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Logger;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;
import com.app.cqrs.command.domain.commands.CreateProductCommand;
import com.app.cqrs.command.domain.exceptions.ExistingProductException;
import com.app.cqrs.command.infrastructure.repositories.ProductLookupRepository;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger logger = Logger.getLogger(CreateProductCommandInterceptor.class.getName());

    private final ProductLookupRepository productLookupRepository;

    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, message) -> {
            if (CreateProductCommand.class.equals(message.getPayloadType())) {
                CreateProductCommand command = (CreateProductCommand) message.getPayload();

                if (command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    this.logger.warning("Invalid product price: " + command.getPrice());
                }

                var existProduct = this.productLookupRepository.existsByIdOrTitle(command.getProductId(),
                        command.getTitle());

                if (existProduct) {
                    throw new ExistingProductException("Product with title " + command.getTitle() + " and id "
                            + command.getProductId() + " already exists.");
                }

                this.logger.info("Creating product: " + command.getTitle() + " with price: " + command.getPrice());

                return message;
            }
            return message;
        };
    }

}
