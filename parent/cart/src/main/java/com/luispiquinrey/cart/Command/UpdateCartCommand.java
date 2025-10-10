package com.luispiquinrey.cart.Command;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.cart.Entities.Item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCartCommand {
    @TargetAggregateIdentifier
    private String idCart;
    private float total;
    private int quantity;
    private List<Item> items;
}
