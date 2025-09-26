package com.luispiquinrey.product.Command.Event;

import com.luispiquinrey.product.Enums.Status;

import lombok.Data;

@Data
public class ProductCreatedEvent {
    private Long idProduct;

    private String name;

    private String brand;

    private Status status;

    private float price;

    private Integer stock;

}
