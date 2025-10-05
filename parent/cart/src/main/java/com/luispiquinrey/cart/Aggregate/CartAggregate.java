package com.luispiquinrey.cart.Aggregate;

import java.util.ArrayList;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.Command.AddProductToCartCommand;
import com.luispiquinrey.DTO.ItemCompact;
import com.luispiquinrey.Enums.StatusCart;
import com.luispiquinrey.Event.AddedProductToCartEvent;
import com.luispiquinrey.Event.ProductRequestedAddToCartEvent;
import com.luispiquinrey.cart.Command.CreateCartCommand;
import com.luispiquinrey.cart.Command.Event.CreatedCartEvent;
import com.luispiquinrey.cart.Entities.Item;

@Aggregate
public class CartAggregate {
    @AggregateIdentifier
    private String idCart;
    private StatusCart status;
    private float total;
    private int quantity;
    public CartAggregate() {
    }
    @CommandHandler
    public CartAggregate(AddProductToCartCommand addProductToOrderCommand){
        AddedProductToCartEvent addedProductToOrderEvent=new AddedProductToCartEvent();
        BeanUtils.copyProperties(addProductToOrderCommand, addedProductToOrderEvent);
        AggregateLifecycle.apply(addedProductToOrderEvent);
    }
    @CommandHandler
    public CartAggregate(CreateCartCommand createOrderCommand){
        CreatedCartEvent createdOrderEvent=new CreatedCartEvent();
        BeanUtils.copyProperties(createOrderCommand, createdOrderEvent);
    }
    @EventSourcingHandler
    public void on(AddedProductToCartEvent event) {
        idCart=event.getIdCart();
        total+=event.getTotal();
        quantity+=event.getQuantity();
    }
    @EventSourcingHandler
    public void on(CreatedCartEvent event){
        this.idCart=event.getIdCart();
    }
}
