package com.luispiquinrey.product.Event;

import lombok.Data;

@Data
public class ProductUpdatedEvent {
    private String idProduct;
    private String name;
    private String brand;
    private float price;
    private Integer stock;
}
