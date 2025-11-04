package com.luispiquinrey.product.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreatedEvent {
    private String idProduct;
    private String name;
    private String idBrand;
    private float price;
    private Integer stock;

}
