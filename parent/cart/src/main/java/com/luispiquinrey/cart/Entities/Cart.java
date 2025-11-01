package com.luispiquinrey.cart.Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luispiquinrey.Entities.AuditInfo;
import com.luispiquinrey.Entities.Target;

import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

@Document("order")
public class Cart extends Target<String> implements Serializable {

    @Id
    private String idCart = UUID.randomUUID().toString();

    private AuditInfo auditInfo;

    @PositiveOrZero
    private BigDecimal total;

    @PositiveOrZero
    @Max(1000)
    private int quantity;

    @Version
    private int version;

    @DBRef
    private List<CartItem> items;

    public Cart() {
    }

    public Cart(BigDecimal total, int quantity, List<CartItem> items) {
        this.total = total;
        this.quantity = quantity;
        this.items = items;
    }

    public Cart(String idCart, AuditInfo auditInfo, BigDecimal total, int quantity, List<CartItem> items) {
        this.idCart = idCart;
        this.auditInfo = auditInfo;
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

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
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

    @Override
    public String toString() {
        return "Cart{" +
                "idCart='" + idCart + '\'' +
                ", auditInfo=" + auditInfo +
                ", total=" + total +
                ", quantity=" + quantity +
                ", version=" + version +
                ", items=" + items +
                '}';
    }
}
