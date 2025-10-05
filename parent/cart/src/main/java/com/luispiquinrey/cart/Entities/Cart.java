package com.luispiquinrey.cart.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luispiquinrey.Entities.AuditInfo;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

@Document("order")
public class Cart implements Serializable{

    @Id
    private String idCart=UUID.randomUUID().toString();

    private AuditInfo auditInfo;

    private float total;

    private int quantity;

    @Version
    private int version;

    @DBRef
    private List<Item> items;

    public Cart() {
    }

    public Cart(@PositiveOrZero float total, @PositiveOrZero @Max(1000) int quantity,
            List<Item> items) {
        this.total = total;
        this.quantity = quantity;
        this.items = items;
    }


    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
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
