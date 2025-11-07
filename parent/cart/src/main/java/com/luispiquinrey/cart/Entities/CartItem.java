package com.luispiquinrey.cart.Entities;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luispiquinrey.Entities.AuditInfo;

@Document(collection = "cart_items")
public class CartItem implements Serializable {

    @Id
    private String idItem=UUID.randomUUID().toString();
    private int quantity;
    private float total;
    private String idCart;
    private String idProduct;

    public CartItem() {
    }

    public CartItem(String idItem) {
        this.idItem = idItem;
    }

    public CartItem(String idItem, int quantity, float total) {
        this.idItem = idItem;
        this.quantity = quantity;
        this.total = total;
    }

    public CartItem(String idCart, String idProduct, int quantity, float total) {
        this.idCart = idCart;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.total = total;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "idItem='" + idItem + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", idCart='" + idCart + '\'' +
                ", idProduct='" + idProduct + '\'' +
                '}';
    }
}