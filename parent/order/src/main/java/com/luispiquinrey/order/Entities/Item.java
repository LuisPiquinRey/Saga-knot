package com.luispiquinrey.order.Entities;

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
    private ArrayList<Order> orders;
    private AuditInfo auditInfo;
    public Item() {
    }
    public Item(String idItem){
        this.idItem=idItem;
    }
    public Item(String idItem, ArrayList<Order> orders) {
        this.idItem = idItem;
        this.orders = orders;
    }
    public String getIdItem() {
        return idItem;
    }
    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }
    public ArrayList<Order> getOrders() {
        return orders;
    }
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
