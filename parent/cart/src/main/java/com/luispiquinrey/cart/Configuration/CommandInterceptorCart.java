package com.luispiquinrey.cart.Configuration;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.luispiquinrey.Command.AddProductToCartCommand;
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
            }else if(AddProductToCartCommand.class.equals(command.getPayloadType())){
                AddProductToCartCommand addProductToOrderCommand=AddProductToCartCommand.builder().build();
                if(addProductToOrderCommand.getIdCart()==null || addProductToOrderCommand.getIdItem()==null)throw new IllegalArgumentException("Id must not be null");
                if(!repositoryOrder.existsById(addProductToOrderCommand.getIdCart()))throw new IllegalArgumentException("You cannot add a product to a order that doesnt exist");
                if(addProductToOrderCommand.getTotal()<0)throw new IllegalArgumentException("Total must be positive");
                if(addProductToOrderCommand.getQuantity()<0 || addProductToOrderCommand.getQuantity()>1000)throw new IllegalArgumentException("Quantity must be positive or less than 1000");
                
            }
            return command;
        };
    }
}
