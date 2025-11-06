package com.luispiquinrey.cart.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRemovedFromCartEvent {
    private String idCart;
    private String idProduct;
}
