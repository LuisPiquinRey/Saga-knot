package com.luispiquinrey.order.Aggregate;

import java.util.ArrayList;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.Command.AddProductToOrderCommand;
import com.luispiquinrey.DTO.ItemCompact;
import com.luispiquinrey.Enums.StatusOrder;
import com.luispiquinrey.Event.ProductRequestedAddToOrderEvent;
import com.luispiquinrey.order.Command.CreateOrderCommand;
import com.luispiquinrey.order.Command.Event.AddedProductToOrderEvent;
import com.luispiquinrey.order.Command.Event.CreatedOrderEvent;
import com.luispiquinrey.order.Entities.Item;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String idOrder;
    private StatusOrder status;
    private float total;
    private int quantity;
    public OrderAggregate() {
    }
    @CommandHandler
    public OrderAggregate(AddProductToOrderCommand addProductToOrderCommand){
        AddedProductToOrderEvent addedProductToOrderEvent=new AddedProductToOrderEvent();
        BeanUtils.copyProperties(addProductToOrderCommand, addedProductToOrderEvent);
        AggregateLifecycle.apply(addedProductToOrderEvent);
    }
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        CreatedOrderEvent createdOrderEvent=new CreatedOrderEvent();
        BeanUtils.copyProperties(createOrderCommand, createdOrderEvent);
    }
    @EventSourcingHandler
    public void on(AddedProductToOrderEvent event) {
        idOrder=event.getIdOrder();
        total+=event.getTotal();
        quantity+=event.getQuantity();
    }
    @EventSourcingHandler
    public void on(CreatedOrderEvent event){
        this.idOrder=event.getIdOrder();
    }
}
