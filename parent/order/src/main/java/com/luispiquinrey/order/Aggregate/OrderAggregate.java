package com.luispiquinrey.order.Aggregate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.luispiquinrey.Enums.StatusOrder;
import com.luispiquinrey.order.Command.AddItemToOrderCommand;
import com.luispiquinrey.order.Command.CreateOrderCommand;
import com.luispiquinrey.order.Command.DeleteOrderCommand;
import com.luispiquinrey.order.Command.RemoveItemFromOrderCommand;
import com.luispiquinrey.order.Command.UpdateItemQuantityCommand;
import com.luispiquinrey.order.Command.UpdateOrderCommand;
import com.luispiquinrey.order.Command.UpdateOrderStatusCommand;
import com.luispiquinrey.order.Entities.OrderItem;
import com.luispiquinrey.order.Event.ItemAddedToOrderEvent;
import com.luispiquinrey.order.Event.ItemQuantityUpdatedEvent;
import com.luispiquinrey.order.Event.ItemRemovedFromOrderEvent;
import com.luispiquinrey.order.Event.OrderCreatedEvent;
import com.luispiquinrey.order.Event.OrderDeletedEvent;
import com.luispiquinrey.order.Event.OrderStatusUpdatedEvent;
import com.luispiquinrey.order.Event.OrderUpdatedEvent;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String idOrder;
    private String idUser;
    private String idAddress;
    private LocalDateTime orderDate;
    private float subtotal;
    private float tax;
    private float shippingCost;
    private float total;
    private StatusOrder status;
    private String notes;
    private Map<String, OrderItem> items = new HashMap<>();

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .idOrder(command.getIdOrder())
                .idUser(command.getIdUser())
                .idAddress(command.getIdAddress())
                .status(command.getStatus() != null ? command.getStatus() : StatusOrder.PENDING)
                .notes(command.getNotes())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(AddItemToOrderCommand command) {
        float discount = command.getDiscount();
        float subtotal = (command.getPricePerUnit() * command.getQuantity()) - discount;

        ItemAddedToOrderEvent event = ItemAddedToOrderEvent.builder()
                .idOrder(command.getIdOrder())
                .idProduct(command.getIdProduct())
                .quantity(command.getQuantity())
                .pricePerUnit(command.getPricePerUnit())
                .discount(discount)
                .subtotal(subtotal)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveItemFromOrderCommand command) {
        ItemRemovedFromOrderEvent event = ItemRemovedFromOrderEvent.builder()
                .idOrder(command.getIdOrder())
                .idProduct(command.getIdProduct())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateItemQuantityCommand command) {
        OrderItem existingItem = items.get(command.getIdProduct());
        float pricePerUnit = existingItem.getSubtotal() / existingItem.getQuantity();
        float newSubtotal = (pricePerUnit * command.getNewQuantity()) - existingItem.getDiscount();

        ItemQuantityUpdatedEvent event = ItemQuantityUpdatedEvent.builder()
                .idOrder(command.getIdOrder())
                .idProduct(command.getIdProduct())
                .newQuantity(command.getNewQuantity())
                .newSubtotal(newSubtotal)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateOrderStatusCommand command) {
        OrderStatusUpdatedEvent event = OrderStatusUpdatedEvent.builder()
                .idOrder(command.getIdOrder())
                .status(command.getStatus())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateOrderCommand command) {
        OrderUpdatedEvent event = OrderUpdatedEvent.builder()
                .idOrder(command.getIdOrder())
                .subtotal(command.getSubtotal())
                .tax(command.getTax())
                .shippingCost(command.getShippingCost())
                .total(command.getTotal())
                .notes(command.getNotes())
                .items(command.getItems())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteOrderCommand command) {
        OrderDeletedEvent event = OrderDeletedEvent.builder()
                .idOrder(command.getIdOrder())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.idOrder = event.getIdOrder();
        this.idUser = event.getIdUser();
        this.idAddress = event.getIdAddress();
        this.orderDate = LocalDateTime.now();
        this.status = event.getStatus();
        this.notes = event.getNotes();
        this.items = new HashMap<>();
        this.subtotal = 0;
        this.tax = 0;
        this.shippingCost = 0;
        this.total = 0;
    }

    @EventSourcingHandler
    public void on(ItemAddedToOrderEvent event) {
        OrderItem item = new OrderItem();
        item.setIdOrderItem(UUID.randomUUID().toString());
        item.setIdProduct(event.getIdProduct());
        item.setQuantity(event.getQuantity());
        item.setSubtotal(event.getSubtotal());
        item.setDiscount(event.getDiscount());

        if (items.containsKey(event.getIdProduct())) {
            OrderItem existingItem = items.get(event.getIdProduct());
            existingItem.setQuantity(existingItem.getQuantity() + event.getQuantity());
            existingItem.setSubtotal(existingItem.getSubtotal() + event.getSubtotal());
        } else {
            items.put(event.getIdProduct(), item);
        }

        recalculateOrderTotals();
    }

    @EventSourcingHandler
    public void on(ItemRemovedFromOrderEvent event) {
        items.remove(event.getIdProduct());
        recalculateOrderTotals();
    }

    @EventSourcingHandler
    public void on(ItemQuantityUpdatedEvent event) {
        OrderItem item = items.get(event.getIdProduct());
        if (item != null) {
            item.setQuantity(event.getNewQuantity());
            item.setSubtotal(event.getNewSubtotal());
            recalculateOrderTotals();
        }
    }

    @EventSourcingHandler
    public void on(OrderStatusUpdatedEvent event) {
        this.status = event.getStatus();
    }

    @EventSourcingHandler
    public void on(OrderUpdatedEvent event) {
        this.subtotal = event.getSubtotal();
        this.tax = event.getTax();
        this.shippingCost = event.getShippingCost();
        this.total = event.getTotal();
        this.notes = event.getNotes();

        if (event.getItems() != null) {
            this.items.clear();
            for (OrderItem item : event.getItems()) {
                this.items.put(item.getIdProduct(), item);
            }
        }
    }

    @EventSourcingHandler
    public void on(OrderDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }

    private void recalculateOrderTotals() {
        float itemsSubtotal = 0;

        for (OrderItem item : items.values()) {
            itemsSubtotal += item.getSubtotal();
        }

        this.subtotal = itemsSubtotal;
        this.total = this.subtotal + this.tax + this.shippingCost;
    }
}
