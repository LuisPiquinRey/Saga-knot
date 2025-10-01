package com.luispiquinrey.order.Entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="item")
public class Item implements Serializable{
    @Id
    private String idItem;
    private String idProduct;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}
        ,targetEntity = Order.class)
    @JoinColumn(name = "idOrder")
    private Order order;

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
