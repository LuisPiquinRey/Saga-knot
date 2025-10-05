package com.luispiquinrey.cart.Entities;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luispiquinrey.Entities.AuditInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Document("item")
public class Item implements Serializable{
    @org.springframework.data.annotation.Id
    private String idItem;

    @DBRef
    private ArrayList<Cart> orders;
    private AuditInfo auditInfo;
    public Item() {
    }
    public Item(String idItem){
        this.idItem=idItem;
    }
    public Item(String idItem, ArrayList<Cart> orders) {
        this.idItem = idItem;
        this.orders = orders;
    }
    public String getIdItem() {
        return idItem;
    }
    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }
    public ArrayList<Cart> getOrders() {
        return orders;
    }
    public void setOrders(ArrayList<Cart> orders) {
        this.orders = orders;
    }
}
