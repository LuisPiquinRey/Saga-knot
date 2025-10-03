package com.luispiquinrey.Event;

import com.luispiquinrey.Enums.Status;

import lombok.Data;

@Data
public class ProductCreatedEvent {
    private String idProduct;

    private String name;

    private String brand;

    private Status status;

    private float price;

    private Integer stock;

}
