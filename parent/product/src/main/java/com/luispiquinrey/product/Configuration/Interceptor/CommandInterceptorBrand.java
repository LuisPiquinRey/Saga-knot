package com.luispiquinrey.product.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Command.CreateBrandCommand;
import com.luispiquinrey.product.Repository.RepositoryBrandLookup;

@Component
public class CommandInterceptorBrand implements MessageDispatchInterceptor<Message<?>> {

    private final RepositoryBrandLookup repositoryBrandLookup;

    @Autowired
    public CommandInterceptorBrand(RepositoryBrandLookup repositoryBrandLookup) {
        this.repositoryBrandLookup = repositoryBrandLookup;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            if (CreateBrandCommand.class.equals(command.getPayloadType())) {
                CreateBrandCommand createBrandCommand = (CreateBrandCommand) command.getPayload();
                if (createBrandCommand.getIdBrand() == null || createBrandCommand.getIdBrand().isEmpty()) {
                    throw new IllegalArgumentException("Brand id cannot be null or empty");
                }
                if (createBrandCommand.getName() == null || createBrandCommand.getName().isBlank()) {
                    throw new IllegalArgumentException("Brand name cannot be null or empty");
                }
                if (repositoryBrandLookup.findById(createBrandCommand.getIdBrand()).isPresent()) {
                    throw new IllegalStateException("Brand is already in database");
                }
            }
            return command;
        };
    }
}
