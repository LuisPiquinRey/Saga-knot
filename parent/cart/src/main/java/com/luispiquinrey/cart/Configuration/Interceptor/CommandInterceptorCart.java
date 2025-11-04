package com.luispiquinrey.cart.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.cart.Command.AddItemToCartCommand;
import com.luispiquinrey.cart.Command.CreateCartCommand;
import com.luispiquinrey.cart.Command.RemoveItemFromCartCommand;
import com.luispiquinrey.cart.Command.UpdateItemQuantityCommand;
import com.luispiquinrey.cart.Repository.RepositoryCart;
import com.luispiquinrey.cart.Repository.RepositoryItem;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandInterceptorCart implements MessageDispatchInterceptor<Message<?>> {

    private final RepositoryCart repositoryCart;
    private final RepositoryItem repositoryItem;

    @Autowired
    public CommandInterceptorCart(RepositoryCart repositoryCart, RepositoryItem repositoryItem) {
        this.repositoryCart = repositoryCart;
        this.repositoryItem = repositoryItem;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            Object payload = command.getPayload();

            if (payload instanceof CreateCartCommand createCart) {
                validateCreateCartCommand(createCart);
            } else if (payload instanceof AddItemToCartCommand addItem) {
                validateAddItemCommand(addItem);
            } else if (payload instanceof RemoveItemFromCartCommand removeItem) {
                validateRemoveItemCommand(removeItem);
            } else if (payload instanceof UpdateItemQuantityCommand updateQuantity) {
                validateUpdateItemQuantityCommand(updateQuantity);
            }

            return command;
        };
    }

    private void validateCreateCartCommand(CreateCartCommand command) {
        if (command.getIdCart() == null || command.getIdCart().isEmpty()) {
            throw new IllegalArgumentException("Cart ID cannot be null or empty");
        }
        if (command.getIdUser() == null || command.getIdUser().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (repositoryCart.existsById(command.getIdCart())) {
            throw new IllegalStateException("Cart already exists with id: " + command.getIdCart());
        }
    }

    private void validateAddItemCommand(AddItemToCartCommand command) {
        if (command.getIdCart() == null || command.getIdCart().isEmpty()) {
            throw new IllegalArgumentException("Cart ID cannot be null or empty");
        }
        if (command.getIdProduct() == null || command.getIdProduct().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (command.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (command.getPricePerUnit() < 0) {
            throw new IllegalArgumentException("Price per unit cannot be negative");
        }
        if (!repositoryCart.existsById(command.getIdCart())) {
            throw new IllegalStateException("Cart does not exist with id: " + command.getIdCart());
        }
    }

    private void validateRemoveItemCommand(RemoveItemFromCartCommand command) {
        if (command.getIdCart() == null || command.getIdCart().isEmpty()) {
            throw new IllegalArgumentException("Cart ID cannot be null or empty");
        }
        if (command.getIdProduct() == null || command.getIdProduct().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (!repositoryCart.existsById(command.getIdCart())) {
            throw new IllegalStateException("Cart does not exist with id: " + command.getIdCart());
        }
    }

    private void validateUpdateItemQuantityCommand(UpdateItemQuantityCommand command) {
        if (command.getIdCart() == null || command.getIdCart().isEmpty()) {
            throw new IllegalArgumentException("Cart ID cannot be null or empty");
        }
        if (command.getIdProduct() == null || command.getIdProduct().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (command.getNewQuantity() <= 0) {
            throw new IllegalArgumentException("New quantity must be greater than zero");
        }
        if (!repositoryCart.existsById(command.getIdCart())) {
            throw new IllegalStateException("Cart does not exist with id: " + command.getIdCart());
        }
    }
}
