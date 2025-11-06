package com.luispiquinrey.product.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Repository.RepositoryProductLookup;

@Component
public class CommandInterceptorProduct implements MessageDispatchInterceptor<Message<?>> {

    private final RepositoryProductLookup repositoryLookup;

    @Autowired
    public CommandInterceptorProduct(RepositoryProductLookup repositoryLookup) {
        this.repositoryLookup = repositoryLookup;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
                if (createProductCommand.getIdProduct().isEmpty())throw new IllegalArgumentException("Product id cannot be null");
                if (createProductCommand.getName() == null || createProductCommand.getName().isBlank())throw new IllegalArgumentException("Product name cannot be null or empty");
                if (createProductCommand.getIdBrand() == null || createProductCommand.getIdBrand().isBlank())throw new IllegalArgumentException("Product brand cannot be null or empty");
                if (createProductCommand.getPrice() < 0)throw new IllegalArgumentException("Product price cannot be negative");
                if (createProductCommand.getStock() < 0)throw new IllegalArgumentException("Product stock cannot be negative");
                if (repositoryLookup.findById(createProductCommand.getIdProduct()).isPresent())throw new IllegalStateException("Product is already in database");
            }
            return command;
        };
    }
}
