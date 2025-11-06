package com.luispiquinrey.order.Projection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.order.Entities.Order;
import com.luispiquinrey.order.Entities.OrderItem;
import com.luispiquinrey.order.Event.ItemAddedToOrderEvent;
import com.luispiquinrey.order.Event.ItemQuantityUpdatedEvent;
import com.luispiquinrey.order.Event.ItemRemovedFromOrderEvent;
import com.luispiquinrey.order.Event.OrderCreatedEvent;
import com.luispiquinrey.order.Event.OrderDeletedEvent;
import com.luispiquinrey.order.Event.OrderStatusUpdatedEvent;
import com.luispiquinrey.order.Event.OrderUpdatedEvent;
import com.luispiquinrey.order.Repository.RepositoryOrder;
import com.luispiquinrey.order.Repository.RepositoryOrderItem;

@Component
@ProcessingGroup("order-collection")
public class OrderProjection {

    private final RepositoryOrder repositoryOrder;
    private final RepositoryOrderItem repositoryOrderItem;

    @Autowired
    public OrderProjection(RepositoryOrder repositoryOrder, RepositoryOrderItem repositoryOrderItem) {
        this.repositoryOrder = repositoryOrder;
        this.repositoryOrderItem = repositoryOrderItem;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        Order order = new Order();
        order.setIdOrder(event.getIdOrder());
        order.setIdUser(event.getIdUser());
        order.setIdAddress(event.getIdAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(event.getStatus());
        order.setNotes(event.getNotes());
        order.setSubtotal(0);
        order.setTax(0);
        order.setShippingCost(0);
        order.setTotal(0);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        repositoryOrder.save(order);
    }

    @EventHandler
    public void on(ItemAddedToOrderEvent event) {
        Optional<Order> orderOptional = repositoryOrder.findById(event.getIdOrder());
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            Optional<OrderItem> existingItemOpt = repositoryOrderItem.findByIdOrderItemAndIdProduct(
                    event.getIdOrder(), event.getIdProduct());

            OrderItem item;
            if (existingItemOpt.isPresent()) {
                item = existingItemOpt.get();
                item.setQuantity(item.getQuantity() + event.getQuantity());
                item.setSubtotal(item.getSubtotal() + event.getSubtotal());
            } else {
                item = new OrderItem();
                item.setIdOrderItem(UUID.randomUUID().toString());
                item.setIdProduct(event.getIdProduct());
                item.setQuantity(event.getQuantity());
                item.setSubtotal(event.getSubtotal());
                item.setDiscount(event.getDiscount());
            }

            repositoryOrderItem.save(item);

            recalculateAndSaveOrder(order);
        }
    }

    @EventHandler
    public void on(ItemRemovedFromOrderEvent event) {
        Optional<Order> orderOptional = repositoryOrder.findById(event.getIdOrder());
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            repositoryOrderItem.deleteByIdOrderAndIdProduct(event.getIdOrder(), event.getIdProduct());
            recalculateAndSaveOrder(order);
        }
    }

    @EventHandler
    public void on(ItemQuantityUpdatedEvent event) {
        Optional<OrderItem> itemOptional = repositoryOrderItem.findByIdOrderItemAndIdProduct(
                event.getIdOrder(), event.getIdProduct());
        if (itemOptional.isPresent()) {
            OrderItem item = itemOptional.get();
            item.setQuantity(event.getNewQuantity());
            item.setSubtotal(event.getNewSubtotal());
            repositoryOrderItem.save(item);

            Optional<Order> orderOptional = repositoryOrder.findById(event.getIdOrder());
            if (orderOptional.isPresent()) {
                recalculateAndSaveOrder(orderOptional.get());
            }
        }
    }

    @EventHandler
    public void on(OrderStatusUpdatedEvent event) {
        Optional<Order> orderOptional = repositoryOrder.findById(event.getIdOrder());
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(event.getStatus());
            order.setUpdatedAt(LocalDateTime.now());
            repositoryOrder.save(order);
        }
    }

    @EventHandler
    public void on(OrderUpdatedEvent event) {
        Optional<Order> orderOptional = repositoryOrder.findById(event.getIdOrder());
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setSubtotal(event.getSubtotal());
            order.setTax(event.getTax());
            order.setShippingCost(event.getShippingCost());
            order.setTotal(event.getTotal());
            order.setNotes(event.getNotes());
            order.setUpdatedAt(LocalDateTime.now());

            if (event.getItems() != null && !event.getItems().isEmpty()) {
                repositoryOrderItem.deleteByIdOrder(event.getIdOrder());

                for (OrderItem item : event.getItems()) {
                    if (item.getIdOrderItem() == null) {
                        item.setIdOrderItem(UUID.randomUUID().toString());
                    }
                    repositoryOrderItem.save(item);
                }
            }

            repositoryOrder.save(order);
        }
    }

    @EventHandler
    public void on(OrderDeletedEvent event) {
        repositoryOrderItem.deleteByIdOrder(event.getIdOrder());
        repositoryOrder.deleteById(event.getIdOrder());
    }

    private void recalculateAndSaveOrder(Order order) {
        List<OrderItem> items = repositoryOrderItem.findByIdOrder(order.getIdOrder());

        float totalSubtotal = 0;

        for (OrderItem item : items) {
            totalSubtotal += item.getSubtotal();
        }

        order.setSubtotal(totalSubtotal);
        order.setTotal(order.getSubtotal() + order.getTax() + order.getShippingCost());
        order.setUpdatedAt(LocalDateTime.now());

        repositoryOrder.save(order);
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) {
        System.err.println("General exception in OrderProjection: " + exception.getMessage());
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void on(IllegalArgumentException exception) {
        System.err.println("IllegalArgumentException in OrderProjection: " + exception.getMessage());
    }
}
