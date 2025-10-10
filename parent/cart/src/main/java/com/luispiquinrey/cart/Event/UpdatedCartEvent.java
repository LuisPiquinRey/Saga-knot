package com.luispiquinrey.cart.Event;

import java.util.List;

import com.luispiquinrey.cart.Entities.Item;

import lombok.Data;

@Data
public class UpdatedCartEvent {
    private String idCart;
    private float total;
    private int quantity;
    private List<Item> items;
}
