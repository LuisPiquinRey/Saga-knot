package com.luispiquinrey.order.Configuration;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.luispiquinrey.Command.AddProductToOrderCommand;
import com.luispiquinrey.order.Command.CreateOrderCommand;
import com.luispiquinrey.order.Repository.RepositoryItem;
import com.luispiquinrey.order.Repository.RepositoryOrder;


public class CommandInterceptorOrder implements MessageDispatchInterceptor<Message<?>>{

    public static final Logger logger=LoggerFactory.getLogger(CommandInterceptorOrder.class);
    private final RepositoryOrder repositoryOrder;
    private final RepositoryItem repositoryItem;

    @Autowired
    public CommandInterceptorOrder(RepositoryOrder repositoryOrder,RepositoryItem repositoryItem){
        this.repositoryOrder=repositoryOrder;
        this.repositoryItem=repositoryItem;
    }
    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            logger.info("Dispatching a command {}.", command);
            if (CreateOrderCommand.class.equals(command.getPayloadType())) {
                CreateOrderCommand createOrderCommand=(CreateOrderCommand)command.getPayload();
                if(createOrderCommand.getIdOrder()==null)throw new IllegalArgumentException("Order id cannot be null");
                if(repositoryOrder.existsById(createOrderCommand.getIdOrder()))throw new IllegalArgumentException("Order already exists");
            }else if(AddProductToOrderCommand.class.equals(command.getPayloadType())){
                AddProductToOrderCommand addProductToOrderCommand=AddProductToOrderCommand.builder().build();
                if(addProductToOrderCommand.getIdOrder()==null || addProductToOrderCommand.getIdItem()==null)throw new IllegalArgumentException("Id must not be null");
                if(!repositoryOrder.existsById(addProductToOrderCommand.getIdOrder()))throw new IllegalArgumentException("You cannot add a product to a order that doesnt exist");
                if(addProductToOrderCommand.getTotal()<0)throw new IllegalArgumentException("Total must be positive");
                if(addProductToOrderCommand.getQuantity()<0 || addProductToOrderCommand.getQuantity()>1000)throw new IllegalArgumentException("Quantity must be positive or less than 1000");
                
            }
            return command;
        };
    }
}
