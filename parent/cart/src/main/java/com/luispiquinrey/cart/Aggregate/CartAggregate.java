package com.luispiquinrey.cart.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.cart.Command.CreateCartCommand;
import com.luispiquinrey.cart.Command.UpdateCartCommand;
import com.luispiquinrey.cart.Event.CreatedCartEvent;
import com.luispiquinrey.cart.Event.UpdatedCartEvent;

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
    @CommandHandler
    public CartAggregate(UpdateCartCommand updateCartCommand){
        UpdatedCartEvent updatedCartEvent=new UpdatedCartEvent();
        BeanUtils.copyProperties(updateCartCommand, updatedCartEvent);
    }
    @EventSourcingHandler
    public void on(CreatedCartEvent event){
        this.idCart=event.getIdCart();
    }
    @EventSourcingHandler
    public void on(UpdatedCartEvent event){
        this.idCart=event.getIdCart();
        this.total=event.getTotal();
        this.quantity=event.getQuantity();
    }
}
