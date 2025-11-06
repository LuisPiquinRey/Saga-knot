package com.luispiquinrey.order.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.order.Command.AddItemToOrderCommand;
import com.luispiquinrey.order.Command.CreateOrderCommand;
import com.luispiquinrey.order.Command.RemoveItemFromOrderCommand;
import com.luispiquinrey.order.Command.UpdateItemQuantityCommand;
import com.luispiquinrey.order.Command.UpdateOrderStatusCommand;
import com.luispiquinrey.order.Repository.RepositoryOrder;
import com.luispiquinrey.order.Repository.RepositoryOrderItem;

@Component
public class CommandInterceptorOrder implements MessageDispatchInterceptor<Message<?>> {

    private static final Logger logger = LoggerFactory.getLogger(CommandInterceptorOrder.class);
    private final RepositoryOrder repositoryOrder;
    private final RepositoryOrderItem repositoryOrderItem;

    @Autowired
    public CommandInterceptorOrder(RepositoryOrder repositoryOrder, RepositoryOrderItem repositoryOrderItem) {
        this.repositoryOrder = repositoryOrder;
        this.repositoryOrderItem = repositoryOrderItem;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            logger.info("Dispatching command: {}", command.getPayloadType().getSimpleName());

            if (CreateOrderCommand.class.equals(command.getPayloadType())) {
                CreateOrderCommand createOrderCommand = (CreateOrderCommand) command.getPayload();
                validateCreateOrderCommand(createOrderCommand);
            } else if (AddItemToOrderCommand.class.equals(command.getPayloadType())) {
                AddItemToOrderCommand addItemCommand = (AddItemToOrderCommand) command.getPayload();
                validateAddItemCommand(addItemCommand);
            } else if (RemoveItemFromOrderCommand.class.equals(command.getPayloadType())) {
                RemoveItemFromOrderCommand removeItemCommand = (RemoveItemFromOrderCommand) command.getPayload();
                validateRemoveItemCommand(removeItemCommand);
            } else if (UpdateItemQuantityCommand.class.equals(command.getPayloadType())) {
                UpdateItemQuantityCommand updateQuantityCommand = (UpdateItemQuantityCommand) command.getPayload();
                validateUpdateItemQuantityCommand(updateQuantityCommand);
            } else if (UpdateOrderStatusCommand.class.equals(command.getPayloadType())) {
                UpdateOrderStatusCommand updateStatusCommand = (UpdateOrderStatusCommand) command.getPayload();
                validateUpdateOrderStatusCommand(updateStatusCommand);
            }

            return command;
        };
    }

    private void validateCreateOrderCommand(CreateOrderCommand command) {
        if (command.getIdOrder() == null || command.getIdOrder().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (command.getIdUser() == null || command.getIdUser().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (command.getIdAddress() == null || command.getIdAddress().isEmpty()) {
            throw new IllegalArgumentException("Address ID cannot be null or empty");
        }
        if (repositoryOrder.existsById(command.getIdOrder())) {
            throw new IllegalStateException("Order already exists with id: " + command.getIdOrder());
        }
    }

    private void validateAddItemCommand(AddItemToOrderCommand command) {
        if (command.getIdOrder() == null || command.getIdOrder().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
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
        if (!repositoryOrder.existsById(command.getIdOrder())) {
            throw new IllegalStateException("Order does not exist with id: " + command.getIdOrder());
        }
    }

    private void validateRemoveItemCommand(RemoveItemFromOrderCommand command) {
        if (command.getIdOrder() == null || command.getIdOrder().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (command.getIdProduct() == null || command.getIdProduct().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (!repositoryOrder.existsById(command.getIdOrder())) {
            throw new IllegalStateException("Order does not exist with id: " + command.getIdOrder());
        }
    }

    private void validateUpdateItemQuantityCommand(UpdateItemQuantityCommand command) {
        if (command.getIdOrder() == null || command.getIdOrder().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (command.getIdProduct() == null || command.getIdProduct().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (command.getNewQuantity() <= 0) {
            throw new IllegalArgumentException("New quantity must be greater than zero");
        }
        if (!repositoryOrder.existsById(command.getIdOrder())) {
            throw new IllegalStateException("Order does not exist with id: " + command.getIdOrder());
        }
    }

    private void validateUpdateOrderStatusCommand(UpdateOrderStatusCommand command) {
        if (command.getIdOrder() == null || command.getIdOrder().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (command.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (!repositoryOrder.existsById(command.getIdOrder())) {
            throw new IllegalStateException("Order does not exist with id: " + command.getIdOrder());
        }
    }
}
