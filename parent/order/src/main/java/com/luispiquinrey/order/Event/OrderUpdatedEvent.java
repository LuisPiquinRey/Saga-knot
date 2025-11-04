package com.luispiquinrey.order.Event;

import java.util.List;

import com.luispiquinrey.order.Entities.OrderItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderUpdatedEvent {
    private String idOrder;
    private float subtotal;
    private float tax;
    private float shippingCost;
    private float total;
    private String notes;
    private List<OrderItem> items;
}
