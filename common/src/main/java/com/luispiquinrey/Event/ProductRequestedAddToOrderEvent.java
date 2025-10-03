package com.luispiquinrey.Event;

import lombok.Data;

@Data
public class ProductRequestedAddToOrderEvent {
    private String idOrder;
    private String idItem;
    private float price;
    private Integer stock;
}
