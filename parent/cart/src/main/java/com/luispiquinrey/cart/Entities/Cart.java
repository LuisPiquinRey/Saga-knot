package com.luispiquinrey.cart.Entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luispiquinrey.Entities.AuditInfo;
import com.luispiquinrey.Entities.BaseEntity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

@Document("carts")
public class Cart extends BaseEntity<String> implements Serializable {

    @Id
    private String idCart = UUID.randomUUID().toString();

    private Long idUser;

    @PositiveOrZero
    private float total;

    @PositiveOrZero
    @Max(1000)
    private int quantity;

    @Version
    private int version;

    @DBRef
    private List<CartItem> items;

    public Cart() {
    }

    public Cart(float total, int quantity, List<CartItem> items) {
        this.total = total;
        this.quantity = quantity;
        this.items = items;
    }

    public Cart(String idCart,float total, int quantity, List<CartItem> items) {
        this.idCart = idCart;
        this.total = total;
        this.quantity = quantity;
        this.items = items;
    }

    public Cart(Long idUser, List<CartItem> items, int quantity, float total) {
        this.idUser = idUser;
        this.items = items;
        this.quantity = quantity;
        this.total = total;
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

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String getId() {
        return idCart;
    }

    @Override
    public void setId(String id) {
        this.idCart = id;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "idCart='" + idCart + '\'' +
                ", total=" + total +
                ", quantity=" + quantity +
                ", version=" + version +
                ", items=" + items +
                '}';
    }
}
