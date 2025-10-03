package com.luispiquinrey.order.Projection;

import java.util.List;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.Command.AddProductToOrderCommand;
import com.luispiquinrey.Enums.StatusOrder;
import com.luispiquinrey.order.Command.Event.AddedProductToOrderEvent;
import com.luispiquinrey.order.Command.Event.CreatedOrderEvent;
import com.luispiquinrey.order.Entities.Item;
import com.luispiquinrey.order.Entities.Order;
import com.luispiquinrey.order.Repository.RepositoryItem;
import com.luispiquinrey.order.Repository.RepositoryOrder;

@Component
@ProcessingGroup("order-collection")
public class OrderProjection {

    private final RepositoryOrder repositoryOrder;
    private final RepositoryItem repositoryItem;

    @Autowired
    public OrderProjection(RepositoryOrder repositoryOrder,RepositoryItem repositoryItem){
        this.repositoryOrder=repositoryOrder;
        this.repositoryItem=repositoryItem;
    }
    @EventHandler
    public void on(CreatedOrderEvent createdOrderEvent){
        Order order=new Order();
        BeanUtils.copyProperties(createdOrderEvent,order);
        repositoryOrder.save(order);
    }
    @EventHandler
    public void on(AddProductToOrderCommand addProductToOrderCommand){
        String idItem=addProductToOrderCommand.getIdItem();
        String idOrder=addProductToOrderCommand.getIdOrder();
        Item item=new Item(idItem);
        repositoryItem.save(item);
        Order order=new Order(StatusOrder.SHIPPED,addProductToOrderCommand.getTotal(),addProductToOrderCommand.getQuantity(),List.of(item));
        repositoryOrder.save(order);
    }
}
