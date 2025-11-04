package com.luispiquinrey.cart.Event;

import java.util.List;

import com.luispiquinrey.cart.Entities.CartItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatedCartEvent {
    private String idCart;
    private float total;
    private int quantity;
    private List<CartItem> items;
}
