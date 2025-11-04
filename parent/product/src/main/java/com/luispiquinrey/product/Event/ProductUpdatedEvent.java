package com.luispiquinrey.product.Event;

import com.luispiquinrey.Enums.StatusProduct;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdatedEvent {
    private String idProduct;
    private String name;
    private String idBrand;
    private float price;
    private Integer stock;
    private StatusProduct status;
}
