package com.luispiquinrey.product.Event;

import java.util.ArrayList;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.luispiquinrey.Enums.StatusProduct;

import lombok.Data;

@Data
public class ProductCreatedEvent {
    private String idProduct;

    private String name;

    private String brand;

    private StatusProduct status;

    private float price;

    private Integer stock;

}
