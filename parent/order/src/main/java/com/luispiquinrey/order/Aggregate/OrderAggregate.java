package com.luispiquinrey.order.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.order.Command.CreateOrderCommand;
import com.luispiquinrey.order.Command.Event.OrderCreatedEvent;
import com.luispiquinrey.order.Enums.Status;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String idOrder;

    private Status status;

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
        this.idOrder=event.getIdOrder();
        this.status=event.getStatus();
    }
}
