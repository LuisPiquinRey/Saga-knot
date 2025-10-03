package com.luispiquinrey.order.Aggregate;

import java.util.ArrayList;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.Command.CreateOrderCommand;
import com.luispiquinrey.DTO.ItemCompact;
import com.luispiquinrey.Enums.StatusOrder;
import com.luispiquinrey.Event.ProductRequestedAddToOrderEvent;
import com.luispiquinrey.order.Command.Event.OrderCreatedEvent;
import com.luispiquinrey.order.Entities.Item;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String idOrder;
    private StatusOrder status;
    private float total;
    private int quantity;
    private String idProduct;
    public OrderAggregate() {
    }
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        OrderCreatedEvent orderCreatedEvent=new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }
    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        idOrder=event.getIdOrder();
        status=event.getStatus();
        total+=event.getTotal();
        quantity+=event.getQuantity();
        idProduct=event.getIdProduct();
    }
}
