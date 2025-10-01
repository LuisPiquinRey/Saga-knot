package com.luispiquinrey.order.Projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.order.Command.Event.OrderCreatedEvent;
import com.luispiquinrey.order.Entities.Order;
import com.luispiquinrey.order.Repository.RepositoryOrder;

@Component
public class OrderProjection {

    private final RepositoryOrder repositoryOrder;

    @Autowired
    public OrderProjection(RepositoryOrder repositoryOrder){
        this.repositoryOrder=repositoryOrder;
    }
    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        Order order=new Order();
        BeanUtils.copyProperties(orderCreatedEvent,order);
        repositoryOrder.save(order);
    }
}
