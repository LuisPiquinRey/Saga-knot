package com.luispiquinrey.cart.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.cart.Command.CreateCartCommand;
import com.luispiquinrey.cart.Event.CreatedCartEvent;

@Aggregate
public class CartAggregate {
    @AggregateIdentifier
    private String idCart;
    private float total;
    private int quantity;
    public CartAggregate() {
    }
    @CommandHandler
    public CartAggregate(CreateCartCommand createOrderCommand){
        CreatedCartEvent createdOrderEvent=new CreatedCartEvent();
        BeanUtils.copyProperties(createOrderCommand, createdOrderEvent);
    }
    @EventSourcingHandler
    public void on(CreatedCartEvent event){
        this.idCart=event.getIdCart();
    }
}
