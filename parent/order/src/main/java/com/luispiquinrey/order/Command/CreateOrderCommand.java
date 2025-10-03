package com.luispiquinrey.order.Command;

import java.util.ArrayList;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.order.Entities.Item;
import com.luispiquinrey.order.Enums.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private Status status;
    private float total;
    private int quantity;
    private ArrayList<Item> items;
}
