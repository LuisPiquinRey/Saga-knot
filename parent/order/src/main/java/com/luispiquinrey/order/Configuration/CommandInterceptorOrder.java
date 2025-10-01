package com.luispiquinrey.order.Configuration;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luispiquinrey.order.Command.CreateOrderCommand;


public class CommandInterceptorOrder implements MessageDispatchInterceptor<Message<?>>{

    public static final Logger logger=LoggerFactory.getLogger(CommandInterceptorOrder.class);
    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            logger.info("Dispatching a command {}.", command);
            if (CreateOrderCommand.class.equals(command.getPayloadType())) {
                CreateOrderCommand createOrderCommand=(CreateOrderCommand)command.getPayload();
                if(createOrderCommand.getIdOrder().isEmpty())throw new IllegalArgumentException("Order id cannot be null");
                if(createOrderCommand.getQuantity() < 0)throw new IllegalArgumentException("Order quantity must be positive or zero and not null");
                if(createOrderCommand.getTotal()< 0 || createOrderCommand.getTotal()>1000)throw new IllegalArgumentException("Order total must be positive or zero and cannot be > than 1000");
            }
            return command;
        };
    }
}
