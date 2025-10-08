package com.luispiquinrey.product.Event;

import lombok.Data;

@Data
public class ProductDeletedEvent {
    private String idProduct;
}
