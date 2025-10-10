package com.luispiquinrey.cart.Event;

import lombok.Data;

@Data
public class UpdatedCartEvent {
    private String idCart;
    private float total;
    private int quantity;
    private List<Item> items;
}
