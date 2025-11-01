package com.luispiquinrey.order.Entities;

import com.luispiquinrey.Enums.StatusOrder;

import jakarta.persistence.Entity;

@Entity
public class Order {
    private String idOrder;
    private float total;
    private StatusOrder status;
}
