package com.luispiquinrey.order.Entities;

import java.io.Serializable;

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
    private String idProduct;
    private AuditInfo auditInfo;
    public Item() {
    }
    public Item(String idItem, String idProduct) {
        this.idItem = idItem;
        this.idProduct = idProduct;
    }
    public String getIdItem() {
        return idItem;
    }
    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }
    public String getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
