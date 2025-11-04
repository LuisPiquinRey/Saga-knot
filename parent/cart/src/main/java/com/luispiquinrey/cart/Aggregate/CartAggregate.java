package com.luispiquinrey.cart.Aggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.luispiquinrey.cart.Command.AddItemToCartCommand;
import com.luispiquinrey.cart.Command.ClearCartCommand;
import com.luispiquinrey.cart.Command.CreateCartCommand;
import com.luispiquinrey.cart.Command.DeleteCartCommand;
import com.luispiquinrey.cart.Command.RemoveItemFromCartCommand;
import com.luispiquinrey.cart.Command.UpdateCartCommand;
import com.luispiquinrey.cart.Command.UpdateItemQuantityCommand;
import com.luispiquinrey.cart.Entities.CartItem;
import com.luispiquinrey.cart.Event.CartClearedEvent;
import com.luispiquinrey.cart.Event.CreatedCartEvent;
import com.luispiquinrey.cart.Event.DeletedCartEvent;
import com.luispiquinrey.cart.Event.ItemAddedToCartEvent;
import com.luispiquinrey.cart.Event.ItemQuantityUpdatedEvent;
import com.luispiquinrey.cart.Event.ItemRemovedFromCartEvent;
import com.luispiquinrey.cart.Event.UpdatedCartEvent;

@Aggregate
public class CartAggregate {

    @AggregateIdentifier
    private String idCart;
    private String idUser;
    private float total;
    private int quantity;
    private Map<String, CartItem> items = new HashMap<>();

    public CartAggregate() {
    }

    @CommandHandler
    public CartAggregate(CreateCartCommand command) {
        CreatedCartEvent event = CreatedCartEvent.builder()
                .idCart(command.getIdCart())
                .idUser(command.getIdUser())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(AddItemToCartCommand command) {
        float total = command.getPricePerUnit() * command.getQuantity();

        ItemAddedToCartEvent event = ItemAddedToCartEvent.builder()
                .idCart(command.getIdCart())
                .idProduct(command.getIdProduct())
                .quantity(command.getQuantity())
                .pricePerUnit(command.getPricePerUnit())
                .total(total)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveItemFromCartCommand command) {
        ItemRemovedFromCartEvent event = ItemRemovedFromCartEvent.builder()
                .idCart(command.getIdCart())
                .idProduct(command.getIdProduct())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateItemQuantityCommand command) {
        CartItem existingItem = items.get(command.getIdProduct());
        float pricePerUnit = existingItem.getTotal() / existingItem.getQuantity();
        float newTotal = pricePerUnit * command.getNewQuantity();

        ItemQuantityUpdatedEvent event = ItemQuantityUpdatedEvent.builder()
                .idCart(command.getIdCart())
                .idProduct(command.getIdProduct())
                .newQuantity(command.getNewQuantity())
                .newTotal(newTotal)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(ClearCartCommand command) {
        CartClearedEvent event = CartClearedEvent.builder()
                .idCart(command.getIdCart())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateCartCommand command) {
        UpdatedCartEvent event = UpdatedCartEvent.builder()
                .idCart(command.getIdCart())
                .total(command.getTotal())
                .quantity(command.getQuantity())
                .items(command.getItems())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteCartCommand command) {
        DeletedCartEvent event = DeletedCartEvent.builder()
                .idCart(command.getIdCart())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CreatedCartEvent event) {
        this.idCart = event.getIdCart();
        this.idUser = event.getIdUser();
        this.total = 0;
        this.quantity = 0;
    }

    @EventSourcingHandler
    public void on(ItemAddedToCartEvent event) {
        CartItem item = new CartItem();
        item.setIdItem(UUID.randomUUID().toString());
        item.setIdCart(event.getIdCart());
        item.setIdProduct(event.getIdProduct());
        item.setQuantity(event.getQuantity());
        item.setTotal(event.getTotal());

        if (items.containsKey(event.getIdProduct())) {
            CartItem existingItem = items.get(event.getIdProduct());
            existingItem.setQuantity(existingItem.getQuantity() + event.getQuantity());
            existingItem.setTotal(existingItem.getTotal() + event.getTotal());
        } else {
            items.put(event.getIdProduct(), item);
        }

        recalculateCartTotals();
    }

    @EventSourcingHandler
    public void on(ItemRemovedFromCartEvent event) {
        items.remove(event.getIdProduct());
        recalculateCartTotals();
    }

    @EventSourcingHandler
    public void on(ItemQuantityUpdatedEvent event) {
        CartItem item = items.get(event.getIdProduct());
        if (item != null) {
            item.setQuantity(event.getNewQuantity());
            item.setTotal(event.getNewTotal());
            recalculateCartTotals();
        }
    }

    @EventSourcingHandler
    public void on(CartClearedEvent event) {
        this.items.clear();
        this.total = 0;
        this.quantity = 0;
    }

    @EventSourcingHandler
    public void on(UpdatedCartEvent event) {
        this.total = event.getTotal();
        this.quantity = event.getQuantity();
        if (event.getItems() != null) {
            this.items.clear();
            for (CartItem item : event.getItems()) {
                this.items.put(item.getIdProduct(), item);
            }
        }
    }

    @EventSourcingHandler
    public void on(DeletedCartEvent event) {
        AggregateLifecycle.markDeleted();
    }

    private void recalculateCartTotals() {
        this.quantity = 0;
        this.total = 0;

        for (CartItem item : items.values()) {
            this.quantity += item.getQuantity();
            this.total += item.getTotal();
        }
    }
}
