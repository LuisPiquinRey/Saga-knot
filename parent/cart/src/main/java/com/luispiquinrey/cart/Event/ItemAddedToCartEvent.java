package com.luispiquinrey.cart.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemAddedToCartEvent {
    private String idCart;
    private String idProduct;
    private int quantity;
    private float pricePerUnit;
    private float total;
}
