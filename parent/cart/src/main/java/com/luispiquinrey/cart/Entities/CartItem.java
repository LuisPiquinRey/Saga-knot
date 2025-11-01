package com.luispiquinrey.cart.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

import com.luispiquinrey.Entities.AuditInfo;

@Document(collection = "cart_items")
public class CartItem implements Serializable {

    @Id
    private String idItem;
    private int quantity;
    private BigDecimal total;
    private AuditInfo auditInfo;

    public CartItem() {
    }

    public CartItem(String idItem) {
        this.idItem = idItem;
    }

    public CartItem(String idItem, int quantity, BigDecimal total, AuditInfo auditInfo) {
        this.idItem = idItem;
        this.quantity = quantity;
        this.total = total;
        this.auditInfo = auditInfo;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "idItem='" + idItem + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", auditInfo=" + auditInfo +
                '}';
    }
}