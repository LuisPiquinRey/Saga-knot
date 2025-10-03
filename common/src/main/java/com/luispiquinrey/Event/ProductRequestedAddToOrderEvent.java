package com.luispiquinrey.Event;

import lombok.Data;

@Data
public class ProductRequestedAddToOrderEvent {
    private String idProduct;
    private String idOrder;
    private float price;
    private Integer stock;
}
