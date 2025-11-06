package com.luispiquinrey.order.Command;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.order.Entities.OrderItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private float subtotal;
    private float tax;
    private float shippingCost;
    private float total;
    private String notes;
    private List<OrderItem> items;
}
