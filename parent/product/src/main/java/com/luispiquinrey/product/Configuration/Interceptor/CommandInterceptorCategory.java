package com.luispiquinrey.product.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Command.CreateCategoryCommand;
import com.luispiquinrey.product.Repository.RepositoryCategoryLookup;

@Component
public class CommandInterceptorCategory implements MessageDispatchInterceptor<Message<?>> {

    private final RepositoryCategoryLookup repositoryCategoryLookup;

    @Autowired
    public CommandInterceptorCategory(RepositoryCategoryLookup repositoryCategoryLookup) {
        this.repositoryCategoryLookup = repositoryCategoryLookup;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            if (CreateCategoryCommand.class.equals(command.getPayloadType())) {
                CreateCategoryCommand createCategoryCommand = (CreateCategoryCommand) command.getPayload();
                if (createCategoryCommand.getIdCategory() == null || createCategoryCommand.getIdCategory().isEmpty()) {
                    throw new IllegalArgumentException("Category id cannot be null or empty");
                }
                if (createCategoryCommand.getName() == null || createCategoryCommand.getName().isBlank()) {
                    throw new IllegalArgumentException("Category name cannot be null or empty");
                }
                if (repositoryCategoryLookup.findById(createCategoryCommand.getIdCategory()).isPresent()) {
                    throw new IllegalStateException("Category is already in database");
                }
            }
            return command;
        };
    }
}
