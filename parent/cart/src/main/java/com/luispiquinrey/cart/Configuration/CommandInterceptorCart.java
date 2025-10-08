package com.luispiquinrey.cart.Configuration;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.luispiquinrey.cart.Command.CreateCartCommand;
import com.luispiquinrey.cart.Repository.RepositoryItem;
import com.luispiquinrey.cart.Repository.RepositoryCart;


public class CommandInterceptorCart implements MessageDispatchInterceptor<Message<?>>{

    public static final Logger logger=LoggerFactory.getLogger(CommandInterceptorCart.class);
    private final RepositoryCart repositoryOrder;
    private final RepositoryItem repositoryItem;

    @Autowired
    public CommandInterceptorCart(RepositoryCart repositoryOrder,RepositoryItem repositoryItem){
        this.repositoryOrder=repositoryOrder;
        this.repositoryItem=repositoryItem;
    }
    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            logger.info("Dispatching a command {}.", command);
            if (CreateCartCommand.class.equals(command.getPayloadType())) {
                CreateCartCommand createOrderCommand=(CreateCartCommand)command.getPayload();
                if(createOrderCommand.getIdCart()==null)throw new IllegalArgumentException("Order id cannot be null");
                if(repositoryOrder.existsById(createOrderCommand.getIdCart()))throw new IllegalArgumentException("Order already exists");
            }
            return command;
        };
    }
}
