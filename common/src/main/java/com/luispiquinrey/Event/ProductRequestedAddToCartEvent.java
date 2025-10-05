package com.luispiquinrey.Event;

import lombok.Data;

@Data
public class ProductRequestedAddToCartEvent {
    private String idCart;
    private String idItem;
    private float price;
    private Integer stock;
    private String name;
    private String brand;
}
