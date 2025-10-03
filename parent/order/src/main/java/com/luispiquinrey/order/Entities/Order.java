package com.luispiquinrey.order.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luispiquinrey.Entities.AuditInfo;
import com.luispiquinrey.Enums.StatusOrder;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

@Document("order")
public class Order implements Serializable{

    @Id
    private String idOrder;

    private StatusOrder status;

    private AuditInfo auditInfo;

    @PositiveOrZero
    private float total;

    @PositiveOrZero
    @Max(value = 1000)
    private int quantity;

    @Version
    private int version;

    @DBRef
    private List<Item> items;

    public Order() {
        this.idOrder = UUID.randomUUID().toString();
        this.status = StatusOrder.PENDING; 
    }

    public Order(StatusOrder status, @PositiveOrZero float total, @PositiveOrZero @Max(1000) int quantity,
            List<Item> items) {
        this.idOrder = UUID.randomUUID().toString();
        this.status = status;
        this.total = total;
        this.quantity = quantity;
        this.items = items;
    }


    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
}
